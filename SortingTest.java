import java.io.*;
import java.util.*;
import java.lang.Math;
public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가? ( 를 나타내는게 isRandom이며 일단 false이다
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// ***************난수일 경우
				isRandom = true;	// 난수임을 표시   (정확히는 난수인지 아닌지를 표시한다)

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// ****************난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'S':	// Shell Sort
						newvalue = DoShellSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t)/* + " ms"*/);
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{

		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
		int swap = 0;
		for (int i = 0; i < value.length ; i++)
		{
			for (int j = 0; j < (value.length - i - 1) ; j++)
			{
				if ( value[j] > value[j+1] )
				{
					swap = value[j];
					value[j] = value[j+1];
					value[j+1] = swap;
				}
				else ;
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{

		int i, j;
		int spare = -2147483648;
		for (i = 0; i < value.length; i++)
		{
			for (spare = value[i], j = i; j>0; j--)
			{
				if (value[j-1] > spare)
				{
					value[j] = value[j-1];
				}
				else
					break;
				}

		value[j] = spare;
		}

		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoShellSort(int[] value)
	{

		int i, j, interval, spare;
		spare = -2147483648; // int의 minvalue
		interval = value.length;
		while (interval> 1)
		{
			for (i = 0; i < value.length; i = i + interval)
			{
				for (spare = value[i], j = i; j>0; j = j - interval)
				{
					if (value[j-interval] > spare)
					{
						value[j] = value[j-interval];
					}
					else
						break;
					}

			value[j] = spare;
			}
		interval = interval / 3 + 1;

		}
		if (interval == 1)
		{
			for (i = 0; i < value.length; i = i + interval)
			{
				for (spare = value[i], j = i; j>0; j = j - interval)
				{
					if (value[j-interval] > spare)
					{
						value[j] = value[j-interval];
					}
					else
						break;
					}

			value[j] = spare;
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		//1. heap 부터 만들기
		for (int i = value.length /2 - 1; i > -1; i--)
		{
			percolateDown(value, i, value.length);
		}
		/*for (int i = 0; i < value.length; i++)
			{System.out.println(value[i]);

			}*/
		//2. delete one by one
		for (int size = value.length - 1; size > -1 ; size--)
		{
			int temp = value[0];
			value[0] = value[size];
			value[size] = temp;
			percolateDown(value, 0, size);

		}
		return (value);
	}
	private static void percolateDown(int[] key, int i, int n)
	{
		int lchild, rchild;
		lchild = 2 * i + 1 ; // left child
		rchild = 2 * i + 2; // right child
		if (lchild < n)
		{
			if ((rchild < n) && (key[lchild] < key[rchild]))
			{
				lchild = rchild; // index of larger child
			}
			if (key[i] < key[lchild])
			{
				int swap = key[i];
				key[i] = key[lchild];
				key[lchild] = swap;
				percolateDown(key, lchild, n);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{

		int[] value1;
		int[] value2;
		if (value.length > 1)
		{
			value1 = new int[value.length / 2];
			value2 = new int[value.length - value.length / 2];
			for (int i = 0 ; i < value1.length; i++)
			{
				value1[i] = value[i];
			}
			for (int j = 0; j < value2.length ; j++)
			{
				value2[j] = value[j + value1.length];
			}

			value1 = DoMergeSort(value1);
			value2 = DoMergeSort(value2);
			value = merge(value1, value2);

		}
		else
			return value;

		return (value);
	}

	private static int[] merge(int[] value1, int[] value2)
	{
		int[] value;
		int i = 0;
		int v1 = 0;
		int v2 = 0;
		value = new int[value1.length + value2.length];

		while (i < value.length)
		{
			if ( v1 == value1.length)
			{
				value[i] = value2[v2];
				v2++;
			}
			else if (v2 == value2.length)
			{
				value[i] = value1[v1];
				v1++;
			}
			else if ( value1[v1] <= value2[v2])
			{
				value[i] = value1[v1];
				v1++;
			}
			else
			{
				value[i] = value2[v2];
				v2++;
			}
			i++;
		}


		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// modification from http://ko.wikipedia.org/wiki/%ED%80%B5_%EC%A0%95%EB%A0%AC
		// : Quick Sort 를 구현하라.
		quicksort(value, 0, value.length - 1);
		return (value);
	}

	private static void quicksort(int[] value, int first, int last)
	{
		int pivotNewIndex;

		if (last > first)
		{
			//pivot = value [first];
			pivotNewIndex = partition(value, first, last, first );
			quicksort(value, first, pivotNewIndex - 1);
			quicksort(value, pivotNewIndex + 1, last);

		}

	}
	private static int partition(int[] value, int first, int last, int pivotIndex)
	{
		int storeIndex;
		int pivotValue;
		int temp;

		pivotValue = value[pivotIndex];
		//swap
		temp = value[pivotIndex];
		value[pivotIndex] = value[last];
		value[last] = temp;
		//end swap
		storeIndex = first;

		for (int i = first; i < last ; i++ )
		{
			if (value[i] <= pivotValue)
			{
				temp = value[storeIndex];
				value[storeIndex] = value[i];
				value[i] = temp;
				storeIndex = storeIndex + 1;
			}



		}
		//swap
		temp = value[last];
		value[last] = value[storeIndex];
		value[storeIndex] = temp;

		return storeIndex;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{

		ArrayList<ArrayList<Integer>> mainlist = new ArrayList<ArrayList<Integer>>();



		int k, i, j, n;

		for (n = 0; n < 10; n++) // 10칸 만큼의 공간 만들기
		{
			ArrayList<Integer> sublist = new ArrayList<Integer>();
			mainlist.add(sublist);

		}//end for n
		for (k = 0; k < 10; k++) // 자릿수 보기
		{
			for (i = 0; i< value.length; i++)
			{
				mainlist.get((value[i] / (int)Math.pow(10,k)) % 10).add(value[i]);
			}
			//여기까지 하면 한 자릿수에 대한 정렬이 끝났다.
			i = 0;
			while(i < value.length)
			{
				for (j = 0; j < 10; j ++)//mainlist의 index
				{
					for (n = 0; n < mainlist.get(j).size(); n++)
					{
						value[i] = mainlist.get(j).get(n);
						i++;
					}
				}
			}// value 치환.

			for (n = 0; n < 10 ; n ++)
			{
				mainlist.get(n).clear();
			}
		}


		return (value);
	}
}
