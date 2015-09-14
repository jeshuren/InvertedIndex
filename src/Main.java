
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main
{
	public static Set<Integer> funNot (Set<Integer> a, Set<Integer> u)
	{
		Set<Integer> b = new HashSet<Integer>();
		b.addAll(u);
		b.removeAll(a);
		return b;		
	}
	public static Set<Integer> funAnd (Set<Integer> a, Set<Integer> u)
	{
		Set<Integer> b =new HashSet<Integer>();
		b.addAll(a);
		b.retainAll(u);
		return b;		
	}
	public static Set<Integer> funOr (Set<Integer> a, Set<Integer> u)
	{
		Set<Integer> b =new HashSet<Integer>();
		b.addAll(u);
		b.addAll(a);
		return b;		
	}
	public static void main(String args[]) throws IOException
	{
		Set<Integer> Universal = new HashSet<Integer>();
		Map<String,Set<Integer>> table = new TreeMap<String,Set<Integer>>();
		String[] input = new String[10];
		int num;
		Scanner key = new Scanner(System.in);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		num = key.nextInt();
		for(int i=0;i<num;i++)
		{
			BufferedReader br = null;
			try
			{
				File file = new File("src/file"+i+".txt");
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				input[i] = br.readLine();
				Universal.add(i);
				String[] temp = input[i].split(" ");
				for(String t:temp)
				{
					if(table.containsKey(t))
					{
						table.get(t).add(i);
					}
					else
					{
						Set<Integer> temp2 = new TreeSet<Integer>();
						temp2.add(i);
						table.put(t, temp2);
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("File Not Found");
				e.printStackTrace();
			}
			finally
			{
				br.close();
			}
		}
		System.out.println("Created Inverted Table:");
		for(String temp: table.keySet())
		{
			System.out.println(temp+"-->"+table.get(temp).toString());
		}
		int no;
		System.out.println("Enter the no. of search querys:");
		no = key.nextInt();
		while(no>0)
		{
			no--;
			System.out.println("Enter the search query:");
			String query = in.readLine();
			String[] query1 = query.split(" ");
			Stack<String> stk = new Stack<String>();
			for(String t1:query1)
			{
				stk.push(t1);
			}
			String reverse = "";
			while(!stk.isEmpty())
			{
				reverse = reverse + stk.pop()+" ";
			}
			Stack<Set<Integer>> intstk = new Stack<Set<Integer>>();
			Stack<String> opstk = new Stack<String>();
			String[] reverse1 = reverse.split(" ");
			String temp;
			for(String t1:reverse1)
			{
				if(!(t1.equals("and") || t1.equals("or") || t1.equals("not")))
		 		{
					if(opstk.isEmpty())
					{
						intstk.push(table.get(t1));
					}
					else
					{
						temp = opstk.pop();
						if(temp.equals("not"))
						{
							intstk.push(funNot(intstk.pop(),Universal));
						}
						else if(temp.equals("and"))
						{
							intstk.push(funAnd(intstk.pop(),table.get(t1)));
						}
						else if(temp.equals("or"))
						{
							intstk.push(funOr(intstk.pop(),table.get(t1)));
						}
					}
				}
				else
				{
					if(t1.equals("not"))
					{
						intstk.push(funNot(intstk.pop(),Universal));
					}
					opstk.push(t1);
				}
			}
			System.out.println(intstk.pop().toString());
		}
	}
}