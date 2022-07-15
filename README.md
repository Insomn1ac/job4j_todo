[![Java CI with Maven](https://github.com/Insomn1ac/job4j_todo/actions/workflows/maven.yml/badge.svg)](https://github.com/Insomn1ac/job4j_todo/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/Insomn1ac/job4j_todo/branch/master/graph/badge.svg?token=N1P4ST7B1P)](https://codecov.io/gh/Insomn1ac/job4j_todo)

<a name="title"><h2>Приложение TODO List</h2></a>

Приложение-список задач. Можно просматривать, редактировать, добавлять и удалять задания.

<a name="tech"><h2>Стек используемых технологий</h2></a>

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)

<a name="tech"><h2>Скриншоты приложения</h2></a>

Страница авторизации пользователя в приложении:
![authorizationPage](src/main/resources/attachments/authorizationPage.png)

Страница регистрации нового аккаунта в приложении:
![registrationPage](src/main/resources/attachments/registrationPage.png)

Страница со всеми заданиями:
![allTasksPage](src/main/resources/attachments/allTasks.png)
У каждого задания в списке можно прочесть его конкретное описание, нажав кнопку "Подробнее".
Переходы ниже таблицы заданий ведут на страницы с выполненными либо невыполненными заданиями, 
также можно просмотреть только задания залогиненного пользователя, либо добавить новое задание.


Страница с завершенными заданиями:
![completedTasksPage](src/main/resources/attachments/complTasks.png)


Страница с новыми заданиями:
![newTasksPage](src/main/resources/attachments/newTask.png)


Страница с заданиями залогиненного в текущий момент пользователя:
![userTasksPage](src/main/resources/attachments/userTasks.png)


Страница добавления нового задания:
![addNewTaskPage](src/main/resources/attachments/addNewTask.png)
При добавлении новому заданию автоматически присваиваются текущие дата/время.


Страница с подробным описанием отдельного завершенного задания:
![completedTaskDescPage](src/main/resources/attachments/completedTaskDescription.png)


Страница с подробным описанием отдельного незавершенного задания:
![newTaskDescPage](src/main/resources/attachments/newTaskDescription.png)
При нажатии на кнопку "Выполнено" задание переходит в статус завершенного.


Страница редактирования задания:
![updateTaskPage](src/main/resources/attachments/taskUpdate.png)
Можно изменять краткое описание задания, отображающееся на странице всех заданий, 
а также полное описание, отображающееся при переходе на конкретное задание.
Также можно изменять статус задания.
При редактировании заданию автоматически присваиваются текущие дата/время.