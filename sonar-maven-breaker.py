import requests
import sys
import time
import argparse

# dobija se uvek poslednji izvestaj kad se pogodi url

URL = "https://sonarcloud.io/api/qualitygates/project_status"

parser = argparse.ArgumentParser()
parser.add_argument("--initialWait", type=int,nargs='?', default=15, const=15)
parser.add_argument("--projectKey", type=str,nargs='?', default="Proba1212Key", const='Proba1212Key')
parser.add_argument("--branch", type=str,nargs='?', default="main", const='main')
parser.add_argument("--pullRequestNumber", type=int,nargs='?', default=-1, const=-1)
parser.add_argument("--testLogs", type=str,nargs='?', default="", const='')
args = parser.parse_args()


PROJECT_KEY = args.projectKey
PROJCT_BRANCH = args.branch
INITIAL_WAIT = args.initialWait
SERVER_LOGS = args.testLogs
PULL_REQUEST_NUMBER = args.pullRequestNumber

TEST_FAILED = False


if "ERROR" in SERVER_LOGS:
    print("========================>>> TESTS FAILED  <<<========================")
    sys.exit(1)

PARAMS = {}
if PULL_REQUEST_NUMBER > 0:
    PARAMS = {'projectKey': PROJECT_KEY, "pullRequest": PULL_REQUEST_NUMBER}
else:
    PARAMS = {'projectKey': PROJECT_KEY, "branch": PROJCT_BRANCH}


# initial sleep because need sometime to upload resuls on server
time.sleep(INITIAL_WAIT)
# see results
r = requests.get(url = URL, params = PARAMS)
status = "ERROR"
response = r.json()
if 'projectStatus' in response:
    if 'status' in response['projectStatus']:
        status = response['projectStatus']['status']
if status == "ERROR":
    print("========================>>> QUALITY GATE ERROR <<<========================")
    sys.exit(1)
else:
    print("========================>>> QUALITY GATE OKEJ <<<========================")
    sys.exit(0)
