# Katrina Ding
# 20 Jul 2020
import psycopg2 # pip install psycopg2
import sys

# global variables
PERSON = 1
COURSE = 0

PERSON_ARG = 7
COURSE_ARG = 5

PERSON_TITLE = "    %-20s|%-30s|%-20s|%-20s|%-10s|%-10s\n" % ("Name", "Email", "PASSWORD", "NICKNAME", "EXP", "CREDIT")
COURSE_TITLE = "    % -10s|%-30s|%-5s|%-5s|%-40s\n" % ("CODE", "NAME", "YEAR", "TERM", "HANDBOOK")

people = []
courses = []


class Person:
    def __init__(self, fname, lname, email, pw, nickname, credit=0, exp=0, id=None):
        self._fname = fname;
        self._lname = lname;
        self._email = email;
        self._pw = pw;
        self._nickname = nickname;
        self._id = id;
        self._credit = credit;
        self._exp = exp;

    @property
    def fname(self):
        return self._fname
     
    @property
    def lname(self):
         return self._lname
     
    @property
    def email(self):
        return self._email
     
    @property
    def pw(self):
         return self._pw
     
    @property
    def nickname(self):
         return self._nickname
     
    @property
    def id(self):
        return self._id

    @property
    def credit(self):
        return self._credit
    
    @property
    def exp(self):
        return self._exp
    
    @property
    def queryAdd(self):
         return f"INSERT INTO Person (id, first_name, last_name, email, password, nick_name, exp, credit) \
             VALUES (uuid_generate_v4(), '{self._fname}', '{self._lname}', '{self._email}', '{self._pw}', '{self._nickname}', {self._exp}, {self._credit})"


    def queryModify(self, fname, lname, email, pw, nickname, credit, exp):
        self.fname = fname
        self.lname = lname
        self.email = email
        self.pw = pw
        self.nickname = nickname
        self.credit = credit
        self.exp = exp
        return f"""UPDATE Person SET 
            first_name = '{self._fname}', 
            last_name = '{self._lname}', 
            email = '{self._email}', 
            password = '{self._pw}', 
            nick_name = '{self._nickname}', 
            credit = {self._credit}, 
            exp = {self._exp} 
            WHERE id = '{self._id}'
            """
     
    @property
    def queryDelete(self):
         return f"DELETE FROM Person WHERE id = '{self._id}'"
     
    @fname.setter
    def fname(self, f):
        self._fname = f if (f != "None") else self._fname
    
    @lname.setter
    def lname(self, l):
        self._lname = l if (l != "None") else self._lname

    @email.setter
    def email(self, email):
        self._email = email if (email != "None") else self._email
 
    @pw.setter
    def pw(self, pw):
        self._pw = pw if (pw != "None") else self._pw
  
    @nickname.setter
    def nickname(self, nn):
        self._nickname = nn if (nn != "None") else self._nickname
        
    @credit.setter
    def credit(self, cd):
        self._credit = cd if (cd != "None") else self._credit
        
    @exp.setter
    def exp(self, exp):
        self._exp = exp if (exp != "None") else self._exp
  
    def __str__(self):
        return "%-20s|%-30s|%-20s|%-20s|%-10d|%-10d" % (self._fname + ' ' + self._lname, self._email, self._pw, self._nickname, self._exp, self._credit)

class Course:
    def __init__(self, code, name, handbook, year, term, id=None):
        self._code = code
        self._name = name
        self._handbook = handbook
        self._term = term
        self._year = year
        self._id = id
        
    @property
    def code(self):
        return self._code

    @property
    def name(self):
        return self._name

    @property
    def handbook(self):
        return self._handbook

    @property
    def id(self):
        return self._id

    @property
    def term(self):
        return self._term
    
    @property
    def year(self):
        return self._year
    
    @property
    def queryAdd(self):
        return f"INSERT INTO Course VALUES (uuid_generate_v4(), '{self._code}', '{self._name}', '{self._handbook}', {self._term}, {self._year})"
    
    def queryModify(self, code, name, handbook, term, year):
        self.code = code
        self.name = name
        self.handbook = handbook
        self.term = term
        self.year = year
        return f"""UPDATE Course SET
                code = '{self._code}', 
                name = '{self._name}', 
                handbook = '{self._handbook}',
                term = {self._term}, 
                year = {self._year} 
                WHERE id = '{self._id}'
                """
    
    @property
    def queryDelete(self):
        return f"DELETE FROM Course WHERE id = '{self._id}'"

    @code.setter
    def code(self, code):
        self._code = code if (code != "None") else self._code
  
    @name.setter
    def name(self, name):
        self._name = name if (name != "None") else self._name 
    
    @handbook.setter
    def handbook(self, handbook):
        self._handbook = handbook if (handbook != "None") else self._handbook
        
    @term.setter
    def term(self, term):
        self._term = term if (term != "None") else self._term
        
    @year.setter
    def year(self, year):
        self._year = year if (year != "None") else self._year
  
    def __str__(self):
        return "%-10s|%-30s|%-5d|%-5d|%-40s" % (self._code, self._name, self._year, self._term, self._handbook[:30]+'...')
    

def add(code, numOfArg):
    try:
        a = input("-> ")
        alist = a.split()
    except (EOFError, KeyboardInterrupt):
        return False
    else:
        while (len(alist) != numOfArg):
            print("ERROR: Expecting", numOfArg, "arguments, actually getting", len(alist), ", please try again.")
            try:
                a = input("-> ")
                alist = a.split()
            except (EOFError, KeyboardInterrupt):
                return False

        if (code == PERSON):
            obj = Person(alist[0], alist[1], alist[2], alist[3], alist[4], alist[5], alist[6])
            print(f"Inserting {alist[0]} {alist[1]}, please wait...")
            
        elif (code == COURSE):
            obj = Course(alist[0], alist[1], alist[2], alist[3], alist[4])
            print(f"Inserting {alist[0]}, please wait...")
            
        try:
            cursor.execute(obj.queryAdd)
        except Exception as e:
            print("ERROR:", e)
            print("Please try again")
            
    return True

def delete(list, obj):
    try:
        cursor.execute(obj.queryDelete)
    except Exception as e:
        print("ERROR: ", e)
        print("Please try again")
    else:
        list.remove(obj)
        print("Deleted")
        
def modify(code, numOfArg, obj):
    try:
        a = input("-> ")
        alist = a.split()
    except (EOFError, KeyboardInterrupt):
        return False
    else:
        while (len(alist) != numOfArg):
            print("ERROR: Expecting", numOfArg, "arguments, actually getting", len(alist), ", please try again.")
            try:
                a = input("-> ")
                alist = a.split()
            except (EOFError, KeyboardInterrupt):
                return False
        #  fname, lname, email, pw, nickname,  credit=0, exp=0, id=None
        if (code == PERSON):
            print(f"Modifying {obj.fname} {obj.lname}, please wait...")
            try:
                cursor.execute(obj.queryModify(alist[0], alist[1], alist[2], alist[3], alist[4], alist[5], alist[6]))
            except Exception as e:
                print("ERROR:", e)
                print("Please try again")

        elif (code == COURSE):
            print(f"\nModifying {obj.code}, please wait...")
            try:
                cursor.execute(obj.queryModify(alist[0], alist[1], alist[2], alist[3], alist[4]))
            except Exception as e:
                print("ERROR:", e)
                print("Please try again")
            

    return True
        
    
    
def textBlock(msg):
    return f"==============================================\n{msg}\n==============================================\n"

def getAllPeople():
    cursor.execute("SELECT * FROM Person")
    res = cursor.fetchall()
    pS = ""
    pS += PERSON_TITLE
    for id, fname, lname, email, pw, nickname, profileImg, gender, exp, join_date, credit in res:
        p = Person(fname, lname, email, pw, nickname, int(credit), int(exp), id)
        people.append(p)
        pS += f"[{len(people)-1}] " + str(p) + "\n"
        
    if (len(people) == 0):
        print("No data found")
        return None
    
    return pS
        
def getAllCourses():
    cursor.execute("SELECT * FROM Course")
    res = cursor.fetchall()
    cS = ""
    cS += COURSE_TITLE
    for id, code, name, handbook, term, year in res:
        c = Course(code, name, handbook, year, term, id)
        courses.append(c)
        cS += f"[{len(courses)-1}] " + str(c) + "\n"
        
    if (len(courses) == 0):
        print("No data found")
        return None
    
    return cS

actions = """
    [1] add person
    [2] add course
    [3] add video (NOT AVAILABLE)
    [4] add student (person exists) (NOT AVAILABLE)
    [5] add tutor (person exists) (NOT AVAILABLE)
    [6] check/modify/delete any person 
    [7] check/modify/delete any student (NOT AVAILABLE)
    [8] check/modify/delete any tutor (NOT AVAILABLE)
    [9] check/modify/delete any course
    [10] check/modify/delete any video (NOT AVAILABLE)
    Press CTRL+D to quit
"""

# connect to database
conn = None
print("Connecting to database, please be patient...")
try:
    # conn = psycopg2.connect("dbname='ass2'")
    conn = psycopg2.connect("dbname='test' \
                            user='postgres' \
                            host='test.cf8ik1zgfzdt.us-east-2.rds.amazonaws.com' \
                            password='vykanpostgres'")
    conn.autocommit = True
    conn.set_client_encoding('UTF8')
    
except Exception as e:
    print("ERROR: ", e)
    print("Unable to connect to the database, try agian later")
    exit()

else:
    print("successfully connect to the database")
    cursor = conn.cursor()

# continously scan for command
while (True):
    # try to get command from actions list
    try:
        command = input(textBlock(f"Please choose your action:\n{actions}") + "-> ")
        
        try:
            command = int(command)
        except ValueError:
            print("Please try again")
            continue

    # exit if get CTRL+C or CTRL+D, safely disconnect from database
    except (EOFError, KeyboardInterrupt):
        break
    # handle command
    else:
        if (command == 1):
            print("Please input in this format: [first name] [last name] [email] [password] [nickname] [credit] [exp]")
            if (not add(PERSON, PERSON_ARG)):
                break
            
        elif (command == 2):
            print("Please input in this format: [code] [name] [handbook] [year] [term]")
            if (not add(COURSE, COURSE_ARG)):
                break
            
        elif (command == 6):
            # get all people and print first
            pS = ""
            if (len(people) == 0):
                pS = getAllPeople()
            else:
                pS += PERSON_TITLE
                for p in people:
                    pS += f"[{len(people)-1}] " + str(p) + "\n"
            
            c = input("Do you want to only view table? (y/n) -> ")
            if (c == 'y'):
                print(pS)
                continue
            
            # then select a person
            try:
                index = input(textBlock(f"Please select person:\n{pS}") + "-> ")
            except (EOFError, KeyboardInterrupt):
                break
            else:
                # determine to delete or to modify
                try:
                    index = int(index)
                except ValueError:
                    print("Are you sure you are typing integer?? Please try again")
                    continue
                except IndexError:
                    print("Hey no such index ok? Try again~")
                else:
                    tmp = input("Modify (1) or delete (2)? -> ")
                    if (int(tmp) == 1):
                        print("I'm too lazy to write a select menu, please type all the arguments again, type None if don't want to make change at any attribute")
                        print("Please input in this format: [first name][last name][email][password][nickname][credit][exp]")
                        if (not modify(PERSON, PERSON_ARG, people[index])):
                            break
                        else:
                            print(people[index])
                            
                    elif (int(tmp) == 2):
                        delete(people, people[index])
                        
        elif (command == 9):
            
            # get all course and print it out first
            cS = ""
            if (len(courses) == 0):
                cS = getAllCourses()
            else:
                cS += COURSE_TITLE
                for c in courses:
                    cS += f"[{len(courses)-1}] " + str(c) + "\n"
            
            c = input("Do you want to only view table? (y/n) -> ")
            if (c == 'y'):
                print(cS)
                continue
            
            # select a course
            try:
                index = input(textBlock(f"Please select course:\n{cS}") + "-> ")
            except (EOFError, KeyboardInterrupt):
                break
            else:
                
                # determine to modify or delete a course from database
                try:
                    index = int(index)
                except ValueError:
                    print("Are you sure you are typing integer?? Please try again")
                    continue
                except IndexError:
                    print("Hey no such index ok? Try again~")
                else:
                    tmp = input("Modify (1) or delete (2)? -> ")
                    if (int(tmp) == 1):
                        print(
                            "I'm too lazy to write a select menu, please type all the arguments again, including the change that you're willing to make")
                        print(
                            "Please input in this format: [code] [name] [handbook] [year] [term]")
                        if (not modify(COURSE, COURSE_ARG, courses[index])):
                            break
                        else:
                            print(courses[index])

                    elif (int(tmp) == 2):
                        delete(courses, courses[index])

                    

            
        print("Done!")

    print("\n")



print("Closing connection to database, please be patient. DO NOT CTRL+C!")
cursor.close()
conn.close()
print("Done, exiting...")
exit()


