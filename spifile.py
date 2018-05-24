import pyrebase
import serial, time
from datetime import datetime



config = {
  "apiKey": "AIzaSyCUn4CDtM9_KdHZ5nayK03x9D-YZvMNU4w",
  "authDomain": "finaldb-609a5.firebaseapp.com",
  "databaseURL": "https://finaldb-609a5.firebaseio.com/",
  "storageBucket": "finaldb-609a5.appspot.com"
}
 
print (datetime.now().time())
currenttime = str(datetime.now().time())
hour=int(currenttime[:2])

#(h,m,s) = currenttime.split(:)
print(hour)
email = "chinmaybadhe15@gmail.com"
password = "helloo"
firebase = pyrebase.initialize_app(config)
auth = firebase.auth()
user = auth.sign_in_with_email_and_password(email, password)
print (user['idToken'])
db = firebase.database()
try:
    station1 = serial.Serial('/dev/ttyUSB0', 9600,timeout = 1)
    station2= serial.Serial('/dev/ttyS0', 9600,timeout = 1)
    print ("station one Is Ready!!")
    print ('station two is ready!!')
    print ('wave tag')
except serial.SerialException:
        print ('Station 1 is Down or Station 2 is down')
while True:
        print ('wave a tag')
        card_dataa = station1.read(12)
        card_dataa1 = station2.read(12)
        
        card_dataa = card_dataa[1:9]
        card_dataa1 = card_dataa1[1:9]
        print (card_dataa)
        print (card_dataa1)
       
        all_users = db.child("rcoem").child('bus').child("bus1").child("students").get(user['idToken'])
        for user1 in all_users.each():
            if hour >= 12:
                if( user1.key() == card_dataa or user1.key() == card_dataa1):
                    print ("child rfid scanned!")
                    #temp1 = db.child('rcoem').child('bus').child('bus1').child('students').child(card_dataa).child('count').get(user['idToken']).val()
                    if db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('count').get(user['idToken']).val()== 0 :
                        db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'count': 1},user['idToken'])
                        print("first if")
                    elif db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('count').get(user['idToken']).val() == 1 and (db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('evening').get(user['idToken'])).val()==0:
                         print ("evening bit updated")
                         db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'evening':1},user['idToken'])
                         db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'count': 2},user['idToken'])
                    elif db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('count').get(user['idToken']).val() == 2 :
                         db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'count': 1},user['idToken'])
                    elif db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('count').get(user['idToken']).val() == 1 and (db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('evening').get(user['idToken'])).val()==1: 
                         db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'count': 0},user['idToken'])
                         db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'evening':0},user['idToken'])
            else :
                if( user1.key() == card_dataa or user1.key() == card_dataa1):
                    print ("child rfid scanned!!")
                    #temp1 = db.child('rcoem').child('bus').child('bus1').child('students').child(card_dataa).child('count').get(user['idToken']).val()
                    if  db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('count').get(user['idToken']).val()== 0:
                        db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'count': 1},user['idToken'])
                        print("first if")
                    elif db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('count').get(user['idToken']).val() == 1 and (db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('morning').get(user['idToken'])).val()==0:
                        db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'morning':1},user['idToken'])
                        db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'count': 2},user['idToken'])
                        print("morning updated")
                    elif db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('count').get(user['idToken']).val() == 2:
                         db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'count': 1},user['idToken'])
                    elif (db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('count').get(user['idToken']).val()== 1) and (db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).child('morning').get(user['idToken'])).val()==1:
                         db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'count': 0},user['idToken'])
                         db.child('rcoem').child('bus').child('bus1').child('students').child(user1.key()).update({'morning':0},user['idToken'])
                         print("morning updated to 0")
