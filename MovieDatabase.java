import java.io.*;
import java.lang.String;
public class MovieDatabase
{
	static String commander;
	static String genre;
	static String title;
	static String keyword;
	static List movieList;


	public static void main(String[] args)
	{
		movieList = new List();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while(true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;
				organize (input);        //parsing
				command(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}

	}
	private static void command(String input)
	{

		if(commander.equals("PRINT"))
		{
			movieList.printListAll(movieList);
		}
		else if (commander.equals("SEARCH"))
		{
			keyword = genre;
			// 리스트 중 keyword 를 포함하는 것이 있으면 다 출력한다.
			movieList.search(keyword);
		}
		else if (commander.equals("INSERT"))
		{
			movieList.insert(genre, title);
		}
		else if(commander.equals("DELETE"))
		{
			movieList.delete(genre, title);
		}



	}

	public static void organize (String input)
	{
		//쓸데없는 공백을 줄이는 일 +  %% 안에 들어간 걸로 자르는 일
		String[] organized;
		while(input.startsWith(" "))
		{
			input = input.substring(1);
		}

		organized = input.split("% +%|%%| *% *");//뭐 잘 모르겠지만 위에 3줄짜리 주석에서 3번째 일을 함
		if(organized.length > 0)
		{
			commander = organized[0];
			genre = "";
			title = "";

			if(organized.length > 1)
			{
				genre = organized[1];

				if(organized.length > 2)
				{
				title = organized[2];
				}
			}
		}//한 줄이 들어오면 이렇게 나눠서 담는다.
	}
}


class Node
{
	private Node next;
	public Node()
	{
		this.next = null;
	}
	public Node(Node nextNode)
	{
		next = nextNode;
	}
	public Node getNext( )
	{
		return this.next;
	}
	public void setNext(Node nextNode)
	{
		this.next = nextNode;
	}
	// setItem, setNext, getNext similar
}

class GenreNode
{
	private String genre;
	private TitleNode titleHead;
	private GenreNode nextGenre;

	public GenreNode()
	{
		this.nextGenre = null;
		titleHead = new TitleNode();
		this.genre = "";
	}
	public GenreNode (String genreData)
	{
		this.genre = genreData;
		titleHead = new TitleNode();
		nextGenre = null;
	}
	public GenreNode (String genreData, GenreNode nextNode)
	{
		titleHead = new TitleNode();
		this.nextGenre = nextNode;
		this.genre = genreData;

	}
	public String getGenreData ()
	{
		return this.genre;
	}

	public void setGenreData(String genreData)
	{
		this.genre = genreData;
	}

	public TitleNode getTitleHead()
	{
		return this.titleHead;
	}

	public GenreNode getNextGenre( )
	{
		return this.nextGenre;
	}
	public void setNextGenre(GenreNode nextNode)
	{
		this.nextGenre = nextNode;
	}
	public boolean isExistTitle(String title)
	{
		TitleNode curr = this.getTitleHead();
		while(curr != null)
		{
			if(curr.getTitleData().equals(title))
			{
				return true;
			}
			curr = curr.getNextTitle();
		}
		return false;
	}

	public TitleNode findTitlePosition(String title)
	{

		TitleNode curr = this.getTitleHead();
		while(curr.getNextTitle() != null)
		{
			if(curr.getNextTitle().getTitleData().compareToIgnoreCase(title) >= 0)
				break;
			curr = curr.getNextTitle();
		}
		return curr;
	}
	public TitleNode findTitleNode(String title)
	{
		TitleNode curr = this.getTitleHead();
		while(curr != null)
		{
			if(curr.getTitleData().equals(title))
			{
				return curr;
			}
			curr = curr.getNextTitle();
		}
		return null;
	}

}

class TitleNode
{
	private String title;
	private TitleNode nextTitle;

	public TitleNode()
	{
		this.title = "";
		this.nextTitle = null;
	}
	public TitleNode (String titleData)
	{
		this.title = titleData;
		nextTitle = null;
	}
	public TitleNode (String titleData, TitleNode nextNode)
	{
		this.nextTitle = nextNode;
		this.title = titleData;
	}
	public String getTitleData ()
	{
		return this.title;
	}

	public void setTitleData(String titleData)
	{
		this.title = titleData;
	}

	public TitleNode getNextTitle( )
	{
		return this.nextTitle;
	}
	public void setNextTitle(TitleNode nextNode)
	{
		this.nextTitle = nextNode;
	}
}

class List
{
	private GenreNode head;

	public List ()
	{
		head = new GenreNode();
	}
	public List (GenreNode node)
	{
		head = new GenreNode();
		head.setNextGenre(node);
	}
	public GenreNode findGenrePosition(String nodeData)
	{
		GenreNode curr = this.head;
		while(curr.getNextGenre() != null)
		{
			if(curr.getNextGenre().getGenreData().compareToIgnoreCase(nodeData) >= 0)
				break;
			curr = curr.getNextGenre();
		}

		return curr;
	}


	public GenreNode findGenreNode(String genre)
	{
		GenreNode curr = this.head;
		while(curr != null)
		{
			if(curr.getGenreData().equals(genre))
			{
				return curr;
			}
			else curr = curr.getNextGenre();
		}

		return null;
	}

	public void insert(String genre, String title)
	{
		GenreNode genreToInsert = findGenreNode(genre);
		TitleNode titleToInsert;

		if(genreToInsert == null)
		{

				//장르 먼저 만든다.
				genreToInsert = new GenreNode(genre);
				GenreNode temp;
				GenreNode position = findGenrePosition(genre);

				temp = position.getNextGenre();
				position.setNextGenre(genreToInsert);
				genreToInsert.setNextGenre(temp);

		}

		if (genreToInsert.isExistTitle(title) == false)
		{ //장르가 있는 경우이다. 이때는 타이틀만 넣어주면 된다.
				titleToInsert = new TitleNode(title);
				TitleNode temp;
				TitleNode position2 = genreToInsert.findTitlePosition(title);

				temp = position2.getNextTitle();
				position2.setNextTitle(titleToInsert);
				titleToInsert.setNextTitle(temp);
		}
	}


	public void delete(String genre, String title)
	{
		GenreNode genreToDelete = findGenreNode(genre);
		if(genreToDelete == null)
			return ;
		TitleNode titleToDelete = genreToDelete.findTitleNode(title);
		if(titleToDelete == null)
			return ;

		TitleNode titlePrev = genreToDelete.findTitlePosition(title);

		titlePrev.setNextTitle(titleToDelete.getNextTitle());

		if(genreToDelete.getTitleHead().getNextTitle() == null)
		{
			GenreNode genrePrev = findGenrePosition(genre);
			genrePrev.setNextGenre(genreToDelete.getNextGenre());
		}


	}
	public void search(String keyword)
	{
		GenreNode curr = this.head;
		String cache = "";
		for(curr = this.head.getNextGenre(); curr != null; curr = curr.getNextGenre())
		{
			for(TitleNode curr2nd = curr.getTitleHead().getNextTitle(); curr2nd != null; curr2nd = curr2nd.getNextTitle())
			{
				if(curr2nd.getTitleData().contains(keyword))
				{
				System.out.println("(" + curr.getGenreData() + ", " + curr2nd.getTitleData() + ")");
				cache = "*";
				}
			}
		}
		if(cache.equals(""))
		{
			System.out.println("EMPTY");
		}


	}



	public void printListAll(List printList)
	{
		GenreNode curr = printList.head;
		if(curr.getNextGenre() == null)
		{
			System.out.println("EMPTY");
		}
		else
		{
		for(curr = printList.head.getNextGenre(); curr != null; curr = curr.getNextGenre())
		{

			for(TitleNode curr2nd = curr.getTitleHead().getNextTitle(); curr2nd != null; curr2nd = curr2nd.getNextTitle())
			{

				System.out.println("(" + curr.getGenreData() + ", " + curr2nd.getTitleData() + ")");
			}

		}
		}
	}

}
