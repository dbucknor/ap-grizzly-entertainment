![grizzly-entertainment-03](https://github.com/dorianbucknor/ap-grizzly-entertainment/assets/61596562/d77121a9-285b-4cfa-ae47-0e1d60b98d7f)


# Grizzly's Entertainment Rental System

Codebase for Grizzly Entertainment management system. Advanced Programming project.

#### GitHub Repository: https://github.com/dorianbucknor/ap-grizzly-entertainment.git

#### Development Stack

+ Java SDK 17 -https://www.oracle.com/in/java/technologies/downloads/#java17
+ Spring Framework
+ Maven

All these can be installed from the MySql installer below

+ MySql v8.0.34 - https://dev.mysql.com/downloads/mysql/
+ MySql Workbench v8.0.34 - https://dev.mysql.com/downloads/workbench/
+ MySQL Connector/J (JDBC)

> Only **MySql v8.0.34**. Make sure to download the correct one.

### Videos to watch

Entity Relation Diagrams (both):

+ https://youtu.be/QpdhBUYk7Kk?si=D-DHlzgT775q4k5z
+ https://youtu.be/-CuY5ADwn24?si=TbF4I7r1v2yuUn0t

Inversion of Control (IoC):

+ https://youtu.be/EPv9-cHEmQw?si=J_7JraDayzFANzIm
+ https://youtu.be/2ejbLVkCndI?si=Rqvq8axPXW3Z55wK

Unified Modeling Language Diagrams (UML):

+ https://www.youtube.com/watch?v=6XrL5jXmTwM

# Project Contribution Guide

Every contributor should read and follow this guide.

## Setup & Workflow

### Getting the code

1. Clone this repository to your computer
    - run `$ git clone https://github.com/dorianbucknor/ap-grizzly-entertainment.git`
    - > The development `(version)-dev` branch is the main working branch. Only finalised code is to be updated
      to `main`
      branch.
2. Open the project folder
3. Create a new branch for your feature/task.
    - run `$ git checkout -b feat/<my-new-feature>` or `$ git checkout -b task/<my-new-task>`
    - See branch naming conventions below
    - > DO NOT UPDATE `main branch` directly
4. Download all the Maven dependencies to run the app
5. Periodically commit and push your changes

### Keeping up-to-date

To Keep your local project in sync with the main repository:

1. Pull all changes on the development branch
2. Merge the code from `main` or `dev` branches into your branch
3. Download all the new Maven dependencies if any

### Submitting code

1. Make sure your code has all the latest changes
2. Upload your code (`git push`)
3. Open the repository on GitHub and create a Pull Request (PR) from your branch to the `dev` branch
4. Wait for your PR to be approved and merged. You may be asked to make chages before it can be merged

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
* Consider starting the commit message with an applicable emoji (Win + .) (optional):

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
