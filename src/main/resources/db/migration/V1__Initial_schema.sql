CREATE table Employee (
    username varchar(25) PRIMARY KEY,
    password varchar(25),
    Name varchar(25),
    FName varchar(25),
    GFName varchar(25),
    job varchar(25)
);

CREATE table EmployeeLastLogin (
    LoginID INTEGER PRIMARY KEY AUTOINCREMENT,
    u_name varchar(25),
    LastLogin DATETIME,
    FOREIGN KEY (u_name) REFERENCES Employee (username)
);

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
);

Create table MapHolder (
    MapHolderID INTEGER PRIMARY KEY AUTOINCREMENT,
    MapHolderName varchar(25),
    MapHolderFName varchar(25),
    MapHolderGFName varchar(25),
    MapHolderPhoneNum varchar(25),
    MapHolderPhoto BLOB
);

CREATE TABLE Resident_Mapholder (
    RID INTEGER,
    MID INTEGER,
    FOREIGN KEY (RID) REFERENCES Resident (ResidentID),
    FOREIGN KEY (MID) REFERENCES MapHolder (MapHolderID)
);

CREATE TABLE KebeleResident (
    KRID INTEGER PRIMARY KEY AUTOINCREMENT,
    ResidentID INTEGER,
    GivenDate DATE,
    ExpDate DATE,
    ExpirationStatus INTEGER CHECK (ExpirationStatus IN (0, 1)),
    FOREIGN KEY (ResidentID) REFERENCES Resident (ResidentID)
);

CREATE TABLE RenewalPayment (
    RPID INTEGER PRIMARY KEY AUTOINCREMENT,
    ResidentID INTEGER,
    username VARCHAR(25),
    TotalFee INTEGER,
    FOREIGN KEY (ResidentID) REFERENCES Resident (ResidentID),
    FOREIGN KEY (username) REFERENCES Employee (username)
);

CREATE TABLE LostPayment (
    LPID INTEGER PRIMARY KEY AUTOINCREMENT,
    ResidentID INTEGER,
    username VARCHAR(25),
    TotalFee INTEGER,
    FOREIGN KEY (ResidentID) REFERENCES Resident (ResidentID),
    FOREIGN KEY (username) REFERENCES Employee (username)
);

CREATE TABLE CreatePayment (
    RPID INTEGER PRIMARY KEY AUTOINCREMENT,
    ResidentID INTEGER,
    username VARCHAR(25),
    TotalFee INTEGER,
    FOREIGN KEY (ResidentID) REFERENCES Resident (ResidentID),
    FOREIGN KEY (username) REFERENCES Employee (username)
);

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