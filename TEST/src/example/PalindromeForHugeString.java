package example;

import java.util.Arrays;
import java.util.Scanner;

public class PalindromeForHugeString {

public static void main(String[] args) {
// TODO Auto-generated method stub
@SuppressWarnings("resource")
Scanner scan= new Scanner(System.in);
System.out.println("Enter your input value: ");
String s=scan.nextLine();
String rev="",in="" ;
String a[]=s.split(" ");
//String con=Arrays.toString(a);
System.out.println(s.split(" ").length);
/*for(int i=0;i<s.split(" ").length;i++)
{
String rec=a[i];
System.out.println(rec.length());
System.out.println(rec);
for(int j=rec.length()-1;j>=0; j--){
rev=rev+rec.charAt(j);
}
System.out.println(rev);
}*/

for(int i=s.length()-1;i>=0;i--)
{
rev=rev+s.charAt(i);

}
System.out.println(rev);
String w[]=rev.split(" ");
int count=s.split(" ").length;
for(int q=0;q<s.split(" ").length;q++)
{
count--;
System.out.println(count);
if(w[count].equalsIgnoreCase(a[q]))

{

System.out.println("Its a palindrome: "+a[q]);
}else{
System.out.println("Its not a palindrome: "+a[q]);	
}
}
/*String b[]=rev.split(" ");
System.out.println(b.length);

for(int r=a.length-1;r>=0;r--)
{
in=in+a[r];
}
System.out.println(in);*/
/*if(a[k]==b[k]){System.out.println("It's palindrome"+a);}	
else{System.out.println("It's not palindrome"+a);}*/

}

}