This application written in Java 17 and Spring Boot 3.1.0 is using GitHub Developer API to find and list
all repositories on GitHub
from the specified user which are not fork. By sending HTTP request "GET" 
to URL http://localhost:8080/api/getRepoByUser ,
for example http://localhost:8080/api/getRepoByUser?name=TomaszSap,
the API returns list of repositories with name, 
owner name and for every one of them list of branches
with their names and last commit sha. The main  mechanism that uses the GitHub Developer Api is in the GitApi 
class in the com.tsapiszczak.githubapi package. This class is responsible for connecting to the API and
returning the list of repositories(findReposByUser() method)
as well as details for each repository(findRepoBranchDetails() method). Single result in the list looks like this:
<br />
        
        {
        "repositoryName": "RepositoryName", 
        "ownersLogin": "Owner",
        "gitBranches": [
                    {
                        "name": "master",
                        "lastCommitSha": "11111111111111111111111111"
                    }
                ]
        }
if you use the wrong Accept value in the header, i.e. application/xml, the application returns code 406,
if the user does not exist, the application returns a 404 code. 