# Grizzly's Entertainment Rental System

Codebase for Grizzly Entertainment management system. Advanced Programming project.

### GitHub Repo: https://github.com/dorianbucknor/ap-grizzly-entertainment.git

#### Development Stack

+ Java SDK 17 -https://www.oracle.com/in/java/technologies/downloads/#java17
+ MySql v8.0.34 - https://dev.mysql.com/downloads/mysql/
+ MySql Workbench v8.0.34 - https://dev.mysql.com/downloads/workbench/
+ MySQL Connector/J (JDBC)
+ Spring Framework
+ Maven

# Project Contribution Guide

Every contributor should read and follow this guide.

## Setup & Workflow

### Getting the code

1. Clone this repository to your computer
    - run `$ git clone https://github.com/dorianbucknor/ap-grizzly-entertainment.git

2. Open the project folder
3. Create a new branch for your feature/task.
    - run `$ git checkout -b feat/<my-new-feature>`
    - See branch naming conventions below
4. Download all the Maven dependencies to run the app
5. Periodically commit and push your changes

### keeping up-to-date

To Keep your local project in sync with the main repository:

6. Pull all changes on the development branch
7. Merge the code from main into your branch
8. Download all the new Maven dependencies if any

### Submitting code

9. Make sure your code has all the latest changes
10. Upload your code (`git push`)
11. Open the repository on GitHub and create a Pull Request (PR) from your branch to the `dev` branch
12. Wait for your PR to be approved and merged. You may be asked to make chages before it can be merged

> NB: Limit the amount of files you modify in each pull request to reduce merge conflicts when multiple people overwrite
> the same file

---
---

### Git Branch Naming Conventions

- for new features: `feat/featurename`
- for fixing a bug: `fix/bugname`

> Avoid long branch names, or branch names with special characters. Use dashes or underscores to separate words.

### Git Commit Messages

* State what you did eg `"created navigationbar"`
* Consider starting the commit message with an applicable emoji (Win + .):

    * 🎨: for changes to UI
    * 🛠 or 🔧: for fixing an issue
    * ✅: for fixing git conflicts or PR changes
    * ⬇️: for adding or removing dependencies
    * 🔥: for removing files

Example: `git commit -m "🎨 finish homepage UI"`

---

## Notes

> Give details in the description when creating a pull request.

> Always add a message to your commits explaining what you've done.