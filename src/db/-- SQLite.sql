-- SQLite

INSERT into Employee VALUES ("abe","sdf","jd","von","lix","Kebele Manager");
INSERT into Employee VALUES ("jdd","sdf","jd","von","lix","System Administrator");
INSERT into Employee VALUES ("nat","sdf","jd","von","lix","Information Officer");
INSERT into Employee VALUES ("abc", "xyz", "John", "Doe", "Buddy", "System Administrator");
INSERT into Employee VALUES ("bak", "xyz", "John", "Doe", "Buddy", "Finance Officer");

INSERT into Employee VALUES ("efg", "hij", "Jane", "Smith", "Luna", "Data Analyst");

INSERT into Employee VALUES ("klm", "nop", "Sarah", "Johnson", "Max", "Project Manager");

INSERT into Employee VALUES ("qrs", "tuv", "Michael", "Williams", "Charlie", "Sales Representative");

INSERT into Employee VALUES ("wxy", "zab", "Emily", "Brown", "Milo", "Graphic Designer");

INSERT into Employee VALUES ("cde", "fgh", "David", "Miller", "Lucy", "Marketing Specialist");

INSERT into Employee VALUES ("ijk", "lmn", "Jennifer", "Anderson", "Rocky", "HR Coordinator");

INSERT into Employee VALUES ("opq", "rst", "Matthew", "Taylor", "Bella", "Financial Analyst");

INSERT into Employee VALUES ("uvw", "xyz", "Jessica", "Garcia", "Max", "Customer Support Representative");

INSERT into Employee VALUES ("def", "ghi", "Daniel", "Wilson", "Cooper", "Operations Manager");

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

    CREATE table EmployeeLastLogin (
        LoginID INTEGER PRIMARY KEY AUTOINCREMENT,
        u_name varchar(25),
        LastLogin DATETIME,
        FOREIGN KEY (u_name) REFERENCES Employee (username)
    )    

    Create table Resident (
        ResidentID INTEGER PRIMARY KEY AUTOINCREMENT,
        Name varchar(25),
        FName varchar(25),
        GFName varchar(25),
        DOB DATE,
        POB varchar(25),
        PhoneNumber varchar(25),
        MotherName varchar(25),
        Sex varchar(10),
        Citizenship varchar(25),
        MaritalStatus varchar(10),
        Job varchar(25),
        BloodType varchar(5),
        HouseNumber varchar(25),
        ECF varchar(25),
        ECP varchar(25),
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
    
    CREATE TABLE KebeleResident (
        KRID INTEGER PRIMARY KEY AUTOINCREMENT,
        ResidentID INTEGER,
        GivenDate DATE,
        ExpDate DATE,
        ExpirationStatus INTEGER CHECK (ExpirationStatus IN (0,1)),
        FOREIGN KEY (ResidentID) REFERENCES Resident (ResidentID)
    )

    drop table KebeleResidentID  

    CREATE TABLE RenewalPayment (
        RPID INTEGER PRIMARY KEY AUTOINCREMENT,
        ResidentID INTEGER,
        username VARCHAR(25),
        TotalFee INTEGER,
        FOREIGN KEY (ResidentID) REFERENCES Resident (ResidentID),
        FOREIGN KEY (username) REFERENCES Employee (username)
        )
    
    CREATE TABLE LostPayment (
        LPID INTEGER PRIMARY KEY AUTOINCREMENT,
        ResidentID INTEGER,
        username VARCHAR(25),
        TotalFee INTEGER,
        FOREIGN KEY (ResidentID) REFERENCES Resident (ResidentID),
        FOREIGN KEY (username) REFERENCES Employee (username)
        )

    CREATE TABLE CreatePayment (
        RPID INTEGER PRIMARY KEY AUTOINCREMENT,
        ResidentID INTEGER,
        username VARCHAR(25),
        TotalFee INTEGER,
        FOREIGN KEY (ResidentID) REFERENCES Resident (ResidentID),
        FOREIGN KEY (username) REFERENCES Employee (username)
        )

    CREATE TABLE Request (
        RequestID INTEGER PRIMARY KEY AUTOINCREMENT,
        RID INTEGER,
        SealedRequest INTEGER CHECK (SealedRequest IN (0, 1)),
        UnpaidRequest INTEGER CHECK (UnpaidRequest IN (0, 1)),
        ApprovalRequest INTEGER CHECK (ApprovalRequest IN (0, 1)),
        RequestType INTEGER CHECK (RequestType IN (0, 1, 2)),
        RequestDate DATETIME,
        FOREIGN KEY (RID) REFERENCES Resident (ResidentID)
    );

drop table Request
delete from Request
     -- added tables

-- Resident table sample data for testing
 
INSERT INTO Resident (Name, FName, GFName, DOB, POB, PhoneNumber, MotherName, Sex, Citizenship, MaritalStatus, Job, BloodType, HouseNumber, ECF, ECP)
VALUES ('John Doe', 'David Doe', 'Michael Doe', '1990-05-15', 'City XYZ', '1234567890', 'Jane Doe', 'Male', 'USA', 'Single', 'Engineer', 'O+', '123 ABC Street', '123456', '7890123456');

INSERT INTO Resident (Name, FName, GFName, DOB, POB, PhoneNumber, MotherName, Sex, Citizenship, MaritalStatus, Job, BloodType, HouseNumber, ECF, ECP)
VALUES ('Jane Smith', 'Robert Smith', 'William Smith', '1985-10-20', 'City ABC', '9876543210', 'Mary Smith', 'Female', 'UK', 'Married', 'Doctor', 'A-', '456 XYZ Street', '789012', '3456789012');

INSERT INTO Resident (Name, FName, GFName, DOB, POB, PhoneNumber, MotherName, Sex, Citizenship, MaritalStatus, Job, BloodType, HouseNumber, ECF, ECP)
VALUES ('Robert Johnson', 'Thomas Johnson', 'Daniel Johnson', '1978-08-02', 'City PQR', '5551234567', 'Emily Johnson', 'Male', 'Canada', 'Married', 'Teacher', 'B+', '789 Main Street', '345678', '9012345678');

INSERT INTO Resident (Name, FName, GFName, DOB, POB, PhoneNumber, MotherName, Sex, Citizenship, MaritalStatus, Job, BloodType, HouseNumber, ECF, ECP)
VALUES ('Sarah Williams', 'Jennifer Williams', 'Elizabeth Williams', '1995-12-10', 'City LMN', '7779876543', 'Jessica Williams', 'Female', 'Australia', 'Single', 'Artist', 'AB-', '456 Elm Street', '567890', '2345678901');

INSERT INTO Resident (Name, FName, GFName, DOB, POB, PhoneNumber, MotherName, Sex, Citizenship, MaritalStatus, Job, BloodType, HouseNumber, ECF, ECP)
VALUES ('Michael Brown', 'Christopher Brown', 'Anthony Brown', '1982-03-25', 'City UVW', '1112223333', 'Michelle Brown', 'Male', 'USA', 'Divorced', 'Lawyer', 'O-', '321 Oak Street', '678901', '8901234567');

INSERT INTO Resident (Name, FName, GFName, DOB, POB, PhoneNumber, MotherName, Sex, Citizenship, MaritalStatus, Job, BloodType, HouseNumber, ECF, ECP)
VALUES ('Emily Davis', 'Jessica Davis', 'Michelle Davis', '1998-06-18', 'City XYZ', '4445556666', 'Linda Davis', 'Female', 'Canada', 'Single', 'Software Engineer', 'A+', '567 Pine Street', '012345', '6789012345');

INSERT INTO Resident (Name, FName, GFName, DOB, POB, PhoneNumber, MotherName, Sex, Citizenship, MaritalStatus, Job, BloodType, HouseNumber, ECF, ECP)
VALUES ('William Wilson', 'Edward Wilson', 'Joseph Wilson', '1975-01-05', 'City ABC', '9998887777', 'Catherine Wilson', 'Male', 'UK', 'Married', 'Businessman', 'AB+', '789 Maple Street', '234567', '9012345678');

INSERT INTO Resident (Name, FName, GFName, DOB, POB, PhoneNumber, MotherName, Sex, Citizenship, MaritalStatus, Job, BloodType, HouseNumber, ECF, ECP)
VALUES ('Olivia Anderson', 'Sophia Anderson', 'Ava Anderson', '1989-09-12', 'City PQR', '2223334444', 'Emma Anderson', 'Female', 'USA', 'Married', 'Architect', 'B-', '123 Oak Street', '456789', '7890123456');

-- KebeleResident table sample data for testing

INSERT INTO KebeleResident (ResidentID, GivenDate, ExpDate, ExpirationStatus) VALUES (6, '2019-01-01', '2022-01-01', 0);
INSERT INTO KebeleResident (ResidentID, GivenDate, ExpDate, ExpirationStatus) VALUES (7, '2014-01-01', '2017-01-01', 1);   
INSERT INTO KebeleResident (ResidentID, GivenDate, ExpDate, ExpirationStatus) VALUES (8, '2010-01-01', '2013-01-01', 1);
INSERT INTO KebeleResident (ResidentID, GivenDate, ExpDate, ExpirationStatus) VALUES (9, '2020-01-01', '2023-01-01', 1);
INSERT INTO KebeleResident (ResidentID, GivenDate, ExpDate, ExpirationStatus) VALUES (10, '2022-01-01', '2025-01-01', 0);
INSERT INTO KebeleResident (ResidentID, GivenDate, ExpDate, ExpirationStatus) VALUES (11, '2023-01-01', '2026-01-01', 0);


