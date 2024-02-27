### Quality Policy

> Describe your Quality Policy in detail for this Sprint (remember what I ask you to do when I talk
> about the "In your Project" part in the lectures and what is mentioned after each assignment (in due
> course you will need to fill out all of them, check which ones are needed for each Deliverable). You
> should keep adding things to this file and adjusting your policy as you go.
> Check in Project: Module Concepts document on Canvas in the Project module for more details

**GitHub Workflow** (start Sprint 1)
> Master/main will always remain stable. You should never work in this branch. We will make a PR at
> the end of the sprint from Dev to master.

> Dev will be the main branch that we use to base everything off of. However, you will very rarely,
> if ever work directly in dev. Dev will be where we keep the most updated branch after a US has been
> updated. Any PR's going from a US into dev will need to be reviewed by at least 1 person.

> US-# will be where you do most of your work. You will be responsible for creating a new branch for
> a user story if you are the first person to work on said user story. You should plan to work on one
> to two tasks at a time, and in an ideal world, only one person will work on a single US at once. If
> two people want to work on the same US at the same time, the second developer will be responsible
> for making a seperate task branch(see below). Every time you work on a US branch before you begin
> working on your task, it is your responsibility to fetch the updates from dev, and add them to your
> US branch. Once a US is finished, it is the dev who worked on the final task's responsibility to
> fetch the dev branch, and make sure that all updates from dev are committed to the US branch. That
> dev will then make a PR to dev (ideally a fast forward, if all updates were fetched and resolved
> properly).

> Task-#-US-# will be an OPTIONAL branch you can choose to make. You do not have to make a new
> branch for each task, as this would create an insane amount of branches. The purpose of making a
> specific task branch, is if someone wants to work on a US where someone else is already working in
> the US branch. It will be up to the second developer to fetch from the US branch to the newly
> created task branch, and when finished make a PR to the matching US branch. Once again, doing this
> is OPTIONAL and only necessary if two people want to work on a US at the exact same time.

> EXAMPLE WORKFLOW:
>> ->I want to work on US#1 Task #1. <br />
> > ->I go into dev and pull if I have no local work that hasn't been committed, so that I get the
> > most recent dev commits. <br />
> > -> If a branch hasn't been made for US#1, I will make one, otherwise I will checkout the US#1
> > branch. <br />
> > -> I do my work and once I am done with my task I get ready to commit. First, I need to check to
> > make sure no updates have been made to dev or US#1 while I was working. If they have been I will
> > fetch those changes and merge them into US#1. <br />
> > -> I then commit my work to US#1 with a commit message styled as such "US-#1 -- Task-#1 Updated
> > QualityPolicy.md to show git workflow" <br />
> > -> I then push my commit to US#1 so that it is in the remote repo on GitHub and post in the
> > kaffeeklatsch_git channel so that team members are tracking a push was made. This doubles as an
> > accountability tool to monitor progress.<br />
> > -> If I worked on the final task for US#1, I will then make a PR from US#1 into dev.(If
> > everything is done correctly, this should be a fast forward)



**Unit Tests Blackbox** (start Sprint 2)
> For any methods that are more complex than basic getter/setter methods we will write Blackbox
> tests that achieve at a minimum 80% code coverage. These tests should be written mainly for code we
> create, but if it is believed to be necessary for already existing code, then test cases should be
> written for those as well.

> The test cases should be developed by the developer responsible for the new code prior to
> submitting a pull request. All reviewers should review the test cases and have the ability to add
> new test cases they believe to be necessary.

> The test cases should be written with Boundary Value Analysis and Equivalence Partitioning in
> mind.

**Unit Tests Whitebox** (online: start Sprint 2, campus: start Sprint 3)
> For any methods that are more complex than basic getter/setter methods we will write Whitebox
> tests that achieve full edge coverage. These tests should be written mainly for code we create, but
> if it is believed to be necessary for already existing code, then test cases should be written for
> those as well.

> The test cases should be developed by the developer responsible for the new code prior to
> submitting a pull request. All reviewers should review the test cases and have the ability to add
> new test cases they believe to be necessary.

> The developer creating the test cases should create a Control Flow Graph to model the method being
> tested. Note, this model does not need to be provided or shared with the rest of the team. It is
> purely for the developer to make creating the test cases easier.

**Code Review** (online: start Sprint 2, campus: start Sprint 2)
> No code or documentation changes should be merged into dev or master without a review.

> Each pull request for a merge into master or dev must be looked at by at least two other team
> members.

> During a review, each member will fill out the review checklist detailed below.

> After the reviews are complete and any deficiencies are corrected ,to include ensuring the merge
> is a fast forward, the pull request can be merged into dev or master.

> The git master for the current sprint should be the one to execute the merge into the dev or
> master branches. The git master does not have to be one of the reviewers for the pull request.



> Checklist/questions list which every developer will need to fill out/answe when creating a Pull
> Request to the Dev branch:

> > -> My code compiles

> > -> My code has been tested and includes unit tests

> > -> My code includes javadoc where appropriate

> > -> My cody is tidy/formatted in a way that is readable

> > -> I have considered proper use of exceptions

> > -> I have made appropriate use of logging

> > -> I have eliminated unused imports

> > -> I have elimated IDE warnings

> > -> I have considered possible Null Pointer exceptions

> > -> The code follows the Coding Standards

> > -> There are no leftover stubs or test routines in the code

> Checklist/question list which every reviewer will need to fill out/anser when conducting a review,
> this checklist (and the answers of course) need to be put into the Pull Request review.

> > -> Comments are comprehensibile and support maintainability

> > -> Comments are not numerous are too wordy

> > -> Types have been generalized where possible

> > -> Parameterized types have been used appropriately

> > -> Exceptions have been used appropriately

> > -> Repetitive code has been factored out

> > -> Methods have all been defined appropriately

> > -> Command classes have been designed to undertake one task only

> > -> Unit tests are present and correct

> > -> Common errors have been checked for

> > -> Potential threading issues have been eliminated where possible








**Static Analysis**  (online: start Sprint 3, campus: start Sprint 3)
> Static Analysis should be done by each member of the team prior to making a pull request. If time
> permits for the developer, static analysis should be done prior to every push to GitHub. There
> should be zero Checkstyle or Spotbugs errors within newly written code. Our goal is to create
> quality code that can be easily expanded upon in the future.

> Throughout the Sprint, if a team member has extra time on their hands, they should work towards
> lowering the total amount of Checkstyle and Spotbugs errors throughout the program. There is no goal
> set for the errors in the old code, but progress towards removing those errors is welcome.

**Continuous Integration**  (start Sprint 3, campus: start Sprint 3)
> Continuous Integration will run for both the master and dev branches every time a pull request or
> commit is made. Only these two branches should be setup for continuous integration as it would
> require a large amount of time to build the program each time any branch has commits made.

> The master branch should always succeed. There should never be a case where the master fails. If
> the master branch fails the Continuous Integration attempt, it is top priority to fix the master and
> ensure it succeeds.

> If the dev branch fails, it is the responsibility of the developer that submitted the code that
> made it fail to fix the branch. All other developers have the ability to help fix the branch. It is
> a high priority to fix this branch as soon as possible in order to resume progress towards
> completing the Sprint. If a pull request is made and the dev branch fails, developers should wait to
> pull the branch into their own repositories for merging until it is fixed.
