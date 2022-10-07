package Assigment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Atm {
	
public static void main(String[] args) {
// TODO Auto-generated method stub
try {
int acno=0,pin=0,flag3=0,witham=0,curbal=0,x=0,y=0,calc=0,flag4=0;
boolean flag=true;
Scanner s=new Scanner(System.in);
Class.forName("com.mysql.cj.jdbc.Driver");
Connection c =DriverManager.getConnection("jdbc:mysql://localhost:3306/mtt","root","root");
Statement stmt=c.createStatement();
ResultSet r;
ResultSet R;
while(flag) {
System.out.println("\n1.Load Cash to ATM\n2.Show Customer Details\n3.Show ATM Operations\n4.Check ATM balance\n5.Exit");
int choice=s.nextInt();
switch(choice) {
case 1:
System.out.println("No of Rs.2000 : ");
int r1=s.nextInt();
System.out.println("No of Rs.500 : ");
int r2=s.nextInt();
System.out.println("No of Rs.100 : ");
int r3=s.nextInt();
stmt.executeUpdate("update atm set Number=Number+"+r1+",Value=Value+"+r1*2000+" where Denomination=2000");
stmt.executeUpdate("update atm set Number=Number+"+r2+",Value=Value+"+r2*500+"  where Denomination=500");
stmt.executeUpdate("update atm set Number=Number+"+r3+",Value=Value+"+r3*100+"  where Denomination=100");
break;
case 2:
r=stmt.executeQuery("select * from custd");
System.out.println("|Acc No|   |Account Holder|   |pin Number|   |Account Balance|");
while(r.next()) {
System.out.println(r.getInt(1)+"       "+r.getString(2)+"           "+r.getInt(3)+"              "+r.getInt(4));
}
break;
case 3:
System.out.println("1.Check Acc Balance\n2.Withdraw Money\n3.Transfer the Money");
int choice2=s.nextInt();
r=stmt.executeQuery("select acc,pin from custd");
switch(choice2) {
case 1:
flag3=0;
System.out.println("Enter the Account no & pin no ");
acno=s.nextInt();
pin=s.nextInt();
while(r.next()) {
if(r.getInt(1)==acno && r.getInt(2)==pin) {
flag3=1;} }
if(flag3==1) {
R=stmt.executeQuery("select * from custd where acc="+acno);
System.out.println("|Acc No|   |Account Holder|   |pin Number|   |Account Balance|");
while(R.next()) {
System.out.println(R.getInt(1)+"       "+R.getString(2)+"           "+R.getInt(3)+"              "+R.getInt(4));
}
}
else {
System.out.println("Invalid AccountNumber and PinNumber");
}
break;
case 2:
flag3=0;
System.out.println("Enter the AccountNumber and PinNumber ");
acno=s.nextInt();
pin=s.nextInt();
while(r.next()) {
if(r.getInt(1)==acno && r.getInt(2)==pin) {
flag3=1;
}
}
if(flag3==1) {

System.out.println("Enter the withdraw amount :");
witham=s.nextInt();
int forc=witham;
int sum=0;
r=stmt.executeQuery("select Value from atm");
while(r.next()) {
sum+=r.getInt(1);
}
if((witham<=10000 && witham>=100)&&witham%100==0) {
if( witham<sum) {
R=stmt.executeQuery("select bal from custd where acc="+acno);
while(R.next()) {
curbal=R.getInt(1);
}
if(witham<curbal) {
while(witham>=3000) {
calc+=2000;
stmt.executeUpdate("update atm set Number=Number-1,Value=Value-2000 where Denomination=2000");
stmt.executeUpdate("update custd set bal=bal-2000 where acc="+acno);
witham-=2000;
}
while(witham>=1000) {
calc+=500;
stmt.executeUpdate("update atm set Number=Number-1,Value=Value-500 where Denomination=500");
stmt.executeUpdate("update custd set bal=bal-500 where acc="+acno);
witham-=500;
}
while(witham>0) {
calc+=100;
stmt.executeUpdate("update atm set Number=Number-1,Value=Value-100 where Denomination=100");
stmt.executeUpdate("update custd set bal=bal-100 where acc="+acno);
witham-=100;
}
if(calc!=forc)
System.out.println("This Denomination is not Available");
else
System.out.println("Take your cash "+calc);

}else {
System.out.println("Entered amount greater than current balance!");
}
}else {
System.out.println("Entered amount not available in ATM");
}
}else {
System.out.println("Entered amount is not in range of 100 to 10,000");
}
}else {
System.out.println("Wrong Account Number and pin Number");
}
break;
case 3:
flag3=0;curbal=0;
System.out.println("Enter Account no and pin ");
acno=s.nextInt();
pin=s.nextInt();
while(r.next()) {
if(r.getInt(1)==acno && r.getInt(2)==pin) {
flag3=1;
}
}
r=stmt.executeQuery("select bal from custd where acc="+acno);
while(r.next()) {
curbal=r.getInt(1);
}
if(flag3==1) {
flag4=0;
R=stmt.executeQuery("select acc from custd");
witham=0;
System.out.println("Enter Amount to transfer");
witham=s.nextInt();
if(witham<10000 && witham>1000) {
System.out.println("Enter the Account Number to Transfer");
int transac=s.nextInt();
while(R.next()) {
if(R.getInt(1)==transac) {
flag4=1;
}
}
if(flag4==1) {
if(witham<curbal) {
stmt.executeUpdate("update custd set bal=bal+"+witham+" where acc="+transac);
stmt.executeUpdate("update custd set bal=bal-"+witham+" where acc="+acno);

}
else {
System.out.println("Amount exceed current balance!");
}
}else {
System.out.println("Entered Account Number is Incorrect!");
}
}else {
System.out.println("Amount not in range of 1000 to 10,000");
}
}else {
System.out.println("Incorrect Pin Number or Acount Number!");
}
break;
}
break;
case 4:
int sum=0;
r=stmt.executeQuery("select * from atm");
System.out.println("Denomination   Number   Value");
while(r.next()) {
System.out.println(r.getInt(1)+"\t\t"+r.getInt(2)+"\t"+r.getInt(3));
sum+=r.getInt(3);
}
System.out.println("Total amount available in ATM= "+sum);
break;
case 5:
flag=false;
break;
}
}
}
catch(Exception e) {
System.out.println(e);
}
}
}