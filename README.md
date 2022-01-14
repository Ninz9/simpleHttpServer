localhost:8080
DB: mySql
test: postman
Post
/users for create new user (name, pass)
/subject for create new subject (title)
/group for create new group (id_teacher, id_subject)
Get
/users?id={id}&flag={1} get 1 user
/users?id={id}$flag={any other than 1} get groups which study user(not working problem with DTO)
/group?id={id}&flag={1} get 1 group
/group?id={id}&flag={any other than 1} get students who stidy in this group(not working problem with DTO)
Delete
/users/{id}
/subject/{id} (new)
Put
/users?id={id}&newName={new Usernameame} change username
/group?groupId={groupId}&studentId={studentId} add student to group
/group?groupId={groupId}&teacherId={teacherId} change Teacher in group
I've been dealing with table relationships for a long time, I figured it out, I'll add the simplest deletions and get in the near future!
(PS: in the diagram CLASS === GROUP,
I changed it because the IDE was swearing)

POSTMAN: https://go.postman.co/workspace/tjv-semestral-work~5adf4b05-df0e-4861-a5d4-78db3d1f6fed/collection/18200982-8aee8642-d64c-474c-95df-4d55ec6c0dba