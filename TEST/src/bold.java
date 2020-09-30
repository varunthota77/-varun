	import java.util.Scanner;
	import java.util.Arrays;
import java.awt.Font;
import java.awt.Graphics;
	import java.awt.font.TextAttribute;
	import java.text.AttributedString;
	import java.util.Scanner;
	public class bold {
		public static String strArray[];
	private static final String textInBold = null;
	public static Scanner in = null;
	static int Arraysize, n, pos,neg,flag=0,a[], temp1, count=0,newArrCount=0;
	// static int[] arr;
	static int newarr[] = new int[5];
	static int tempsize=0;
	static String Sortvalue;
	static int arr[] = new int[10];
	static int l=2;
	public static void main(String args[])
	{
	try
	{
	System.out.print("Please Enter the Elements Size: ");//user inut size of aray 
	Scanner scan = new Scanner(System.in);
	Arraysize = scan.nextInt();
	tempsize=Arraysize-1;
	}
	catch(Exception e)
	{
	System.out.println("Not a valid element Size");// when we given input as other then numerics it shows validation
	System.out.println("Please Enter valid array size");// again it ask valid input
	main(args);
	}

	Ascdesc();


	}
	public static int[] Ascdesc()
	{
	String YesNo="";

	int in, i, pos;


	Scanner scan = new Scanner(System.in);


	try
	{

	// input array elements
	System.out.print("Please Enter the Elements : ");
	for(i=0; i<Arraysize; i++)
	{
	arr[i] = scan.nextInt();
	}
	}
	catch(Exception e)
	{
	System.out.println("Not a valid Element");// when input given other than numerics it shows validation
	System.out.println("Please enter a valid number");// ask valid numeric
	Ascdesc();
	System.exit(0);
	Arraysize=0;
	count++;
	}
	System.out.println("please enter ascending or descending"); // sort by user input
	Sortvalue = scan.next();




	if(Sortvalue.equalsIgnoreCase("ascending"))// when user select ascending
	{
	for(int x=0;x<arr.length-1;x++)
	{
	for(int y=x+1;y<arr.length-1;y++)
	{
	if(arr[x]>=arr[y])
	{
	int temp=arr[x];
	arr[x]=arr[y];
	arr[y]=temp;
	}
	}
	}
	System.out.println("After sorting the elements are:");// it sort and shows values in ascending order 
	for(int l=1;l<=arr.length-1;l++)
	{
	if(arr[l]==0)
	{

	}
	else
	{
	System.out.println(arr[l]);
	}
	}

	}



	if(Sortvalue.equalsIgnoreCase("descending"))// when user select descending it sort as selected input
	{ 
	for(int x=0;x<arr.length-1;x++)
	{
	for(int y=x+1;y<arr.length-1;y++)
	{
	if(arr[x]>=arr[y])
	{
	int temp=arr[x];
	arr[x]=arr[y];
	arr[y]=temp;
	}
	}
	}

	System.out.println("After sorting the elements are:");
	for(int l=2;l<=Arraysize+1;l++)
	{
	System.out.println(arr[arr.length-l]);

	}


	}




	for(int k=0;k<=100;k++)
	{
	if(count>0)
	{
	break;
	}
	System.out.println("Do You want to insert New elements ?");// user need to add new insert 
	System.out.println("Enter Yes or No");
	YesNo = scan.next();
	if(YesNo.equalsIgnoreCase("yes"))// USER INPUT YES
	{

	Arraysize++;

	System.out.printf(" Enter Element to be Insert : ");// input inserted element


	in = scan.nextInt();

	newarr[k]=in;


	arr[Arraysize-1]=in;


	System.out.print("Element inserted Successfully..!!\n");



	}
	else
	{
	flag++;
	// System.out.print("Now the New Array is :\n");
	for(i=0; i<arr.length-1; i++)
	{
	if(arr[i]==0)
	{

	}
	else
	{
	System.out.println(arr[i]);
	}

	if(Sortvalue.equalsIgnoreCase("descending")) // it sort as descending order
	{ 
	for(int f=0;f<arr.length-1;f++)
	{
	for(int j=f+1;j<arr.length-1;j++)
	{
	if(arr[f]>=arr[j])
	{
	int temp=arr[f];
	arr[f]=arr[j];
	arr[j]=temp;
	}
	}
	}

	System.out.println("After descending sort the elements are:");



	}
	if(Sortvalue.equalsIgnoreCase("ascending"))// it sort as ascending order
	{
	for(int f=0;f<arr.length-1;f++)
	{
	for(int j=f+1;j<arr.length-1;j++)
	{
	if(arr[f]>=arr[j])
	{
	int temp=arr[f];
	arr[f]=arr[j];
	arr[j]=temp;
	}
	}

	}
	System.out.println("After ascending sort the elements are:");
	for(int l=0;l<arr.length-1;l++)
	{
	if(arr[l]==0)
	{

	}
	else
	{






	}

	}




	}
	break;
	}
	if(Sortvalue.equalsIgnoreCase("ascending"))
	{
	for(int t=0;t<=arr.length-1;t++)
	{
	for(int v=0;v<=arr.length-1;v++)
	{
	try
	{



	if(arr[t]==newarr[v])
	{
	if((arr[t]==0)&&(newarr[t]==0))
	{
	break;
	}
	else
	{
	if(arr[t]==0)
	{

	}
	else
	{
	System.out.print(" {"+arr[t]+"}");
	t++;// it increses value size 
	}

	}
	}
	else
	{

	}

	}	catch(Exception e)
	{
	//continue;
	}

	}

	if(arr[t]==0)
	{

	}
	else
	{
	System.out.print(" "+arr[t]);
	}
	}
	}
	else
	{

	for(int t=arr.length-1;t>=0;t--)
	{
	for(int v=arr.length-1;v>=0;v--)
	{
	try
	{



	if(arr[t]==newarr[v])
	{
	if((arr[t]==0)&&(newarr[t]==0))
	{
	break;
	}
	else
	{
	if(arr[t]==0)
	{

	}
	else
	{
	System.out.print(" {"+arr[t]+"}");
	t--;// decreses value size

	}

	}
	}
	else
	{

	}

	}	catch(Exception e)
	{
	continue;
	}

	}



	if(arr[t]==0)
	{

	}
	else
	{
	System.out.print(" "+arr[t]);
	}

	}	

	}



	}//else ending for no case

	if(flag>0)
	{
	break;
	}
	}
	return arr;
	}
 

	}