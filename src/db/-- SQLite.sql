-- SQLite
INSERT into Employee VALUES ("abe","sdf","jd","von","lix","Kebele Manager");
INSERT into Employee VALUES ("jdd","sdf","jd","von","lix","System Administrator");
-- clear the All the tables
DELETE FROM Resident;
DELETE FROM MapHolder;
DELETE FROM Employee;

    CREATE table Employee (
        username varchar(25) PRIMARY KEY,
        password varchar(25),
        Name varchar(25),
        FName varchar(25),
        GFName varchar(25),
        job varchar(25)
    )

    Create table Resident (
        ResidentID INTEGER PRIMARY KEY AUTOINCREMENT,
        Name varchar(25),
        FName varchar(25),
        GFName varchar(25),
        DOB DATE,
        POB varchar(25),
        PhoneNumber varchar(25),
        Sex varchar(10),
        MaritalStatus varchar(10),
        Citizenship varchar(25),
        BloodType varchar(5),
        MotherName varchar(25),
        ECF varchar(25),
        ECP varchar(25),
        Job varchar(25),
        HouseNumber varchar(25),
        ResidentPhoto BLOB
    )

    Create table MapHolder (
        MapHolderID INTEGER PRIMARY KEY AUTOINCREMENT,
        MapHolderName varchar(25),
        MapHolderFName varchar(25),
        MapHolderGFName varchar(25),
        MapHolderPhoneNum varchar(25),
        MapHolderPhoto BLOB
    )

    CREATE TABLE Resident_Mapholder (
        RID INTEGER,
        MID INTEGER,
        FOREIGN KEY (RID) REFERENCES Resident (ResidentID),
        FOREIGN KEY (MID) REFERENCES MapHolder (MapHolderID)
    )

