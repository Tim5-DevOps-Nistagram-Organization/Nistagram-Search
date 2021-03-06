package rs.ac.uns.ftn.devops.tim5.nistagramsearch.kafka.saga;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.kafka.Constants;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper.CampaignMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Reaction;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka.*;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.CampaignService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.PostService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.ReactionService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

@Service
public class Consumer {

    private final UserService userService;
    private final PostService postService;
    private final ReactionService reactionService;
    private final CampaignService campaignService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    @Autowired
    public Consumer(UserService userService,
                    PostService postService,
                    ReactionService reactionService,
                    CampaignService campaignService,
                    KafkaTemplate<String, String> kafkaTemplate,
                    Gson gson) {
        this.userService = userService;
        this.postService = postService;
        this.reactionService = reactionService;
        this.campaignService = campaignService;
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    @KafkaListener(topics = Constants.SEARCH_TOPIC, groupId = Constants.GROUP)
    public void getMessage(String msg) {
        Message message = gson.fromJson(msg, Message.class);
        if (message.getAction().equals(Constants.START_ACTION)) {
            switch (message.getReplayTopic()) {
                case Constants.USER_ORCHESTRATOR_TOPIC:
                    UserMessage userMessage = gson.fromJson(msg, UserMessage.class);
                    try {
                        userService.create(userMessage.getUsername(), userMessage.getEmail(), userMessage.getWebsiteUrl());
                        userMessage.setDetails(userMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                    } catch (Exception e) {
                        userMessage.setDetails(userMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                    }
                    kafkaTemplate.send(userMessage.getTopic(), gson.toJson(userMessage));
                    break;
                case Constants.POST_ORCHESTRATOR_TOPIC:
                    PostMessage postMessage = gson.fromJson(msg, PostMessage.class);
                    try {
                        Post post = new Post(postMessage.getPostId(), postMessage.getMediaId(), postMessage.getUsername(), postMessage.getTags());
                        postService.addNewPost(post);
                        postMessage.setDetails(postMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                    } catch (Exception e) {
                        postMessage.setDetails(postMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                    }
                    kafkaTemplate.send(postMessage.getTopic(), gson.toJson(postMessage));
                    break;
                case Constants.USER_SETTINGS_ORCHESTRATOR_TOPIC:
                    SettingsMessage settingsMessage = gson.fromJson(msg, SettingsMessage.class);
                    try {
                        userService.setSettings(settingsMessage.getUsername(), settingsMessage.isPrivate());
                        settingsMessage.setDetails(settingsMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                    } catch (Exception e) {
                        settingsMessage.setDetails(settingsMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                    }
                    kafkaTemplate.send(settingsMessage.getTopic(), gson.toJson(settingsMessage));
                    break;
                case Constants.REACTION_ORCHESTRATOR_TOPIC:
                    ReactionMessage reactionMessage = gson.fromJson(msg, ReactionMessage.class);
                    try {
                        Reaction reaction = new Reaction(
                                reactionMessage.getReactionId(),
                                ReactionEnum.of(reactionMessage.getReactionValue()),
                                reactionMessage.getPostId(),
                                reactionMessage.getUsername());
                        reactionService.addNewReaction(reaction);
                        reactionMessage.setDetails(reactionMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                    } catch (Exception e) {
                        reactionMessage.setDetails(reactionMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                    }
                    kafkaTemplate.send(reactionMessage.getTopic(), gson.toJson(reactionMessage));
                    break;
                // delete post if it marked as unappropriated
                case Constants.UNAPPROPRIATED_CONTENT_ORCHESTRATOR_TOPIC:
                    ContentReportMessage reportMessage = gson.fromJson(msg, ContentReportMessage.class);
                    try {
                        postService.deleteByPostId(reportMessage.getPostId());
                        reportMessage.setDetails(reportMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                    } catch (Exception e) {
                        reportMessage.setDetails(reportMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                    }
                    kafkaTemplate.send(reportMessage.getTopic(), gson.toJson(reportMessage));
                    break;
                // add new campaign
                case Constants.CAMPAIGN_ORCHESTRATOR_TOPIC:
                    CampaignMessage campaignMessage = gson.fromJson(msg, CampaignMessage.class);
                    try {
                        Campaign campaign = CampaignMapper.toEntity(campaignMessage);
                        campaignService.add(campaign);
                        campaignMessage.setDetails(campaignMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                    } catch (Exception e) {
                        campaignMessage.setDetails(campaignMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                    }
                    kafkaTemplate.send(campaignMessage.getTopic(), gson.toJson(campaignMessage));
                    break;
                case Constants.USER_FOLLOW_ORCHESTRATOR_TOPIC:
                    FollowMessage followMessage = gson.fromJson(msg, FollowMessage.class);
                    try {
                        userService.follow(followMessage.getUserUsername(), followMessage.getFollowingUsername(),
                                followMessage.getFollowAction());
                        followMessage.setDetails(followMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        followMessage.setDetails(followMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                    }
                    kafkaTemplate.send(followMessage.getTopic(), gson.toJson(followMessage));
                    break;
            }
        }
        // For Entity update on original microservice
        else if (message.getAction().equals(Constants.UPDATE_ACTION) && message.getReplayTopic().equals(Constants.REACTION_ORCHESTRATOR_TOPIC)) {
            ReactionMessage reactionMessage = gson.fromJson(msg, ReactionMessage.class);
            try {
                Reaction reaction = new Reaction(
                        reactionMessage.getReactionId(),
                        ReactionEnum.of(reactionMessage.getReactionValue()),
                        reactionMessage.getPostId(),
                        reactionMessage.getUsername());
                reactionService.updateReaction(reaction);
                reactionMessage.setDetails(reactionMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
            } catch (Exception e) {
                reactionMessage.setDetails(reactionMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
            }
            kafkaTemplate.send(reactionMessage.getTopic(), gson.toJson(reactionMessage));
        }
        // For Entity delete on original microservice
        else if (message.getAction().equals(Constants.DELETE_ACTION)) {
            if (message.getReplayTopic().equals(Constants.REACTION_ORCHESTRATOR_TOPIC)) {
                ReactionMessage reactionMessage = gson.fromJson(msg, ReactionMessage.class);
                try {
                    reactionService.deleteReaction(reactionMessage.getReactionId());
                    reactionMessage.setDetails(reactionMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                } catch (Exception e) {
                    reactionMessage.setDetails(reactionMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                }
                kafkaTemplate.send(reactionMessage.getTopic(), gson.toJson(reactionMessage));
            }
            if (message.getReplayTopic().equals(Constants.POST_ORCHESTRATOR_TOPIC)) {
                PostMessage postMessage = gson.fromJson(msg, PostMessage.class);
                try {
                    postService.deleteByPostId(postMessage.getPostId());
                    postMessage.setDetails(postMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                } catch (Exception e) {
                    postMessage.setDetails(postMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                }
                kafkaTemplate.send(postMessage.getTopic(), gson.toJson(postMessage));
            }
            if (message.getReplayTopic().equals(Constants.CAMPAIGN_ORCHESTRATOR_TOPIC)) {
                CampaignMessage campaignMessage = gson.fromJson(msg, CampaignMessage.class);
                try {
                    campaignService.delete(campaignMessage.getCampaignId());
                    campaignMessage.setDetails(campaignMessage.getReplayTopic(),
                            Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                } catch (Exception e) {
                    campaignMessage.setDetails(campaignMessage.getReplayTopic(),
                            Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                }
                kafkaTemplate.send(campaignMessage.getTopic(), gson.toJson(campaignMessage));
            }

        }
    }
}