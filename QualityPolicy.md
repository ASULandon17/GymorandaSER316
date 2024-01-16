### Quality Policy
> Describe your Quality Policy in detail for this Sprint (remember what I ask you to do when I talk about the "In your Project" part in the lectures and what is mentioned after each assignment (in due course you will need to fill out all of them, check which ones are needed for each Deliverable). You should keep adding things to this file and adjusting your policy as you go.
> Check in Project: Module Concepts document on Canvas in the Project module for more details 

**GitHub Workflow** (start Sprint 1)
 >Master/main will always remain stable.  You should never work in this branch.  We will make a PR at the end of the sprint from Dev to master.

 >Dev will be the main branch that we use to base everything off of.  However, you will very rarely, if ever work directly in dev. Dev will be where we keep the most updated branch after a US has been updated.  Any PR's going from a US into dev will need to be reviewed by at least 1 person.

 >US-# will be where you do most of your work.  You will be responsible for creating a new branch for a user story if you are the first person to work on said user story.  You should plan to work on one to two tasks at a time, and in an ideal world, only one person will work on a single US at once.  If two people want to work on the same US at the same time, the second developer will be responsible for making a seperate task branch(see below).  Every time you work on a US branch before you begin working on your task, it is your responsibility to fetch the updates from dev, and add them to your US branch.  Once a US is finished, it is the dev who worked on the final task's responsibility to fetch the dev branch, and make sure that all updates from dev are committed to the US branch.  That dev will then make a PR to dev (ideally a fast forward, if all updates were fetched and resolved properly).

 >Task-#-US-# will be an OPTIONAL branch you can choose to make.  You do not have to make a new branch for each task, as this would create an insane amount of branches.  The purpose of making a specific task branch, is if someone wants to work on a US where someone else is already working in the US branch.  It will be up to the second developer to fetch from the US branch to the newly created task branch, and when finished make a PR to the matching US branch. Once again, doing this is OPTIONAL and only necessary if two people want to work on a US at the exact same time.

 >EXAMPLE WORKFLOW:
 >> ->I want to work on US#1 Task #1. <br />
 >> ->I go into dev and pull if I have no local work that hasn't been committed, so that I get the most recent dev commits. <br />
 >> -> If a branch hasn't been made for US#1, I will make one, otherwise I will checkout the US#1 branch. <br />
 >> -> I do my work and once I am done with my task I get ready to commit. First, I need to check to make sure no updates have been made to dev or US#1 while I was working.  If they have been I will fetch those changes and merge them into US#1. <br />
 >> -> I then commit my work to US#1 with a commit message styled as such "US-#1 -- Task-#1   Updated QualityPolicy.md to show git workflow" <br />
 >> -> I then push my commit to US#1 so that it is in the remote repo on GitHub. <br />
 >> -> If I worked on the final task for US#1, I will then make a PR from US#1 into dev.(If everything is done correctly, this should be a fast forward)
  


**Unit Tests Blackbox** (start Sprint 2)
  > Describe your Blackbox testing policy 

 **Unit Tests Whitebox** (online: start Sprint 2, campus: start Sprint 3)
  > Describe your Whitebox testing policy 

**Code Review** (online: start Sprint 2, campus: start Sprint 2)
  > Describe your Code Review policy for on campus it is ok to have a less formal process in Sprint 2, should be updated in Sprint 3 though

  > Include a checklist/questions list which every developer will need to fill out/answe when creating a Pull Request to the Dev branch. 

  > Include a checklist/question list which every reviewer will need to fill out/anser when conducting a review, this checklist (and the answers of course) need to be put into the Pull Request review.

**Static Analysis**  (online: start Sprint 3, campus: start Sprint 3)
  > Your Static Analysis policy   

**Continuous Integration**  (start Sprint 3, campus: start Sprint 3)
  > Your Continuous Integration policy
