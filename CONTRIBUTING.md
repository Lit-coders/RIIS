# RIIS

A JavaFX application for the Resident ID Issuing System (RIIS) for the Kebeles of Bahir Dar City Administration. 

# Contributing to RIIS

When contributing to this repository, please first discuss the change you wish to make via issue,
email, or any other method with the owners of this repository before making a change. 

Please note we have a code of conduct, please follow it in all your interactions with the project.

When contributing to this repository, please first follow the [Steps to Contribute](#steps-to-contribute) and the [Code of Conduct](#code-of-conduct) before contributing to this project.

Check [The basic path of your contribution](#the-basic-path-of-your-contribution) to get a basic idea of the contribution process.

## Steps to Contribute
### 1. Setting up your local development environment

1. Fork the repository from the [RIIS repository](https://github.com/Lit-Coders/RIIS/tree/dev) to your account.
2. Clone the repository to your local machine.
```bash
git clone <your-forked-repository-link>
```
3. Create a new branch from the remote `dev` branch.
```bash
git checkout -b <your_name + dev> origin/dev
```
4. Make your changes to the code base.
5. Commit and push your changes to your forked repository to the branch you created.
```bash
git push origin <your_name + dev>
```
6. Create a pull request from your forked repository to the `dev` branch 
    of the [RIIS repository](https://github.com/Lit-Coders/RIIS/tree/dev).
7. Wait for the pull request to be reviewed and merged.

### 2. Code of Conduct

1. Use self-explanatory variable names, function names, and class names.
2. Use comments to explain complex code.
3. Use descriptive commit messages.
4. Use descriptive pull request titles and descriptions.

### 3. Specific Contribution Guidelines

1. Pulling changes from the original (organization) repository to your forked repository:
    - Add the original repository as an upstream to your local repository.
    ```bash
    git remote add upstream https://github.com/Lit-coders/RIIS.git
    ```
    - Pull the changes from the original repository to your local repository.
    ```bash
    git pull upstream main
    ```
    - Push the changes from your local repository to your forked repository. 
    ```bash
    git push origin HEAD:dev
    ```

2. Pushing changes from your local repository to your forked repository after making changes:
    ```bash
    git push origin HEAD:dev
    ```

3. If you are contributing a new page:
    - Create a new fxml file in the `src/main/resources/fxml` directory.
    - Create a new css file in the `src/main/resources/css` directory. (It is recommended to name the css file as `fxml_file_name + .css`)
    - Create a new controller class in the `src/main/java/com/riis/Controller` directory.(It is recommended to name the controller class as `fxml_file_name + Controller`)
    - make the controller class implement the controller interface. (Check the `src/main/java/com/riis/Controller/Controller.java` file for the interface)
    - If there is an asset like images to be used in the page, add it to the `src/main/resources/images` directory.

4. To be continued . . . 


## The basic path of your contribution

1. You forked the repository to your account.
2. You cloned the repository to your local machine.
3. You created a new branch from the remote `dev` branch.
4. You switch to the branch you created.

### You will repeat these steps whenever you want to contribute to the project

5. You made your changes to the code base.
6. You committed and pushed your changes to your forked repository to the branch you created.
7. You created a pull request from your forked repository to the `dev` branch of the original repository.
8. You waited for the pull request to be reviewed and merged.
9. You pulled the changes from the original repository to your local repository `main` branch.
10. You made the branch you created up-to-date with the `main` branch.
11. You push both the `main` branch and the branch you created to your forked repository. 
    - This is because when you pull from the original repo it doesn't update your forked repo but only your local repo
11. Make another change . . . 

### Key points to remember

- Remember you will have 2 branches in your local repository. The `main` branch and the branch you created.
- Always push with the branch you created and pull with the `main` branch.
- Always make sure the branch you created is up-to-date with the `main` branch.
- Always make sure the `main` branch is up-to-date with the `main` branch of the original repository.
