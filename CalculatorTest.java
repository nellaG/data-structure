import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;
import java.lang.Double;
import java.lang.String;

public class CalculatorTest
{
	static Stack<Double> operandDeck = new Stack<Double>();
	static Stack<String> operatorDeck = new Stack<String>();
	static Stack<String> stackDeck = new Stack<String>();
	static Stack<String> finalDeck = new Stack<String>();
	static Stack<String> cloneDeck = new Stack<String>();
	static boolean isError = false;
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
				operandDeck.clear();
				operatorDeck.clear();
				stackDeck.clear();
				finalDeck.clear();
				cloneDeck.clear();
				isError = false;
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input)
	{



		if(!braceChecker(input)) //괄호 체크 관문
		{
			System.out.println("ERROR");
			return ;
		}




		try //괄호 외에 잘못된 입력 체크 + 스택 넣기 관문
		{
			StringTokenizer st = new StringTokenizer(input, "+^-~/%(*)");
			String stt;
			while(st.hasMoreTokens())
			{
				stt = st.nextToken().trim();
				if(stt.length() > 0)
				{
					Double operands = Double.parseDouble(stt);
				}
			}

		}
		catch (NumberFormatException e)
		{
			System.out.println("ERROR");
			return ;
		}



		stackOperator(input);
		if (isError == true)
		{
			System.out.println("ERROR");
			return ;
		}
		stackCalculation();


	}


	public static boolean braceChecker (String input){

		Stack<Character> stackBrace = new Stack<Character>();
		char brace;

		for (int ch = 0; ch < input.length(); ch++)
		{
			brace = input.charAt(ch);
			if ( brace == '(')
			{
				stackBrace.push(brace);
			}//end if
			else if (brace == ')')
			{
				if (!stackBrace.empty())
				{
					stackBrace.pop();
				}//end if
				else
					return false;
			}//end elif
		}//end for
		return stackBrace.empty();
	}



	public static void stackOperator(String input) // postfix로 변환 , 연산자 오류 체크
	{
		input = input.replaceAll("\\s", "");
		Stack<String> braceDeck = new Stack<String>();
		boolean previousOperator = false;
		boolean previousOpenBrace = false;
		//연산자 오류 체크 (binary operator 중복 입력)
		if (input.startsWith("-"))
		{
			input = "~" + input.substring(1);
		}

		for(int i = 0; i < input.length(); i++)
		{
			if(input.charAt(i) == '-' && previousOpenBrace)
			{
				input = input.substring(0, i) + "~" + input.substring(i + 1);
			}

			if(isBinaryOperator(input.charAt(i)))
			{
				if(previousOperator)
				{
					isError = true;
					return ;
				}
				else
				{
					previousOperator = true;
					previousOpenBrace = false;
				}
			}
			else if (input.charAt(i) == '(')
			{
				previousOpenBrace = true;
				previousOperator = false;
			}
			else
			{
				previousOperator = false;
				previousOpenBrace = false;
			}
		}//end for. 연산자 오류 체크 끝



		//stack 만들기

		StringTokenizer st = new StringTokenizer(input, "+^-~/%(*)", true);


		while(st.hasMoreTokens())
		{
			String stt = st.nextToken();
			double operand = -1;
			boolean isOperand = false;

			try
			{
				operand = Double.parseDouble(stt);
				isOperand = true;
			}
			catch (NumberFormatException e)
			{
				isOperand = false;
			}

			if(isOperand)
			{
				stackDeck.push(stt);
			}
			else if (!isOperand)
			{
				if(stt.equals("("))
				{
					braceDeck.push(stt);
				}
				else if(stt.equals(")"))
				{
					while(!braceDeck.peek().equals("("))
					{
						stackDeck.push(braceDeck.pop());
					}
					braceDeck.pop();
				}//end if
				else //operator일때
				{
					while(!braceDeck.empty() && !braceDeck.peek().equals("(") && precedence(stt) <= precedence(braceDeck.peek()))
					{
						stackDeck.push(braceDeck.pop());
					} //end while
					braceDeck.push(stt);
				}
			}
		}//end while
		while(!braceDeck.empty())
		{

			stackDeck.push(braceDeck.pop());

		}
	}//end stackoperator





	public static int precedence(String operator)
	{
		if( operator.equals("(") || operator.equals(")"))
		{
			return 5;
		}
		else if( operator.equals("^"))
		{
			return 4;
		}
		else if( operator.equals("~"))
		{
			return 3;
		}
		else if( operator.equals("*") || operator.equals("/") || operator.equals("%"))
		{
			return 2;
		}
		else if(operator.equals ("+") || operator.equals("-"))
		{
			return 1;
		}
		else
			return -1;
	}


	public static boolean isBinaryOperator(char x)
	{
		if(x == '+' || x == '-' || x == '%' || x == '/' || x == '^' || x == '*')
		{
			return true;
		}

			return false;

	}


	public static void stackCalculation()
	{
		double operand1;
		double operand2;
		int operint1;
		int operint2;
		double result;

		String printstr = "";
		String operatorPack = "*^~%/+-";
		while(!stackDeck.empty())
		{
			String k = stackDeck.pop();
			finalDeck.push(k);
			cloneDeck.push(k);
		}

		while(!cloneDeck.empty())
		{
			printstr = printstr + " " + cloneDeck.pop();
		}


		//연산부분
		while(!finalDeck.empty())
		{
			String top = finalDeck.pop();
			if (operatorPack.contains(top))
			{
				if (top.equals("~"))
				{
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand1 = operandDeck.pop();

					result = 0 - operand1;
					operandDeck.push(result);
				}
				else if (top.equals("+"))
				{
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand2 = operandDeck.pop();
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand1 = operandDeck.pop();

					result = operand1 + operand2;
					operandDeck.push(result);
				}
				else if( top.equals("*"))
				{
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand2 = operandDeck.pop();
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand1 = operandDeck.pop();

					result = operand1 * operand2;
					operandDeck.push(result);
				}
				else if (top.equals("^"))
				{
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand2 = operandDeck.pop();
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand1 = operandDeck.pop();
					operint2 = (int) operand2;
					operint1 = (int) operand1;
					if ((operand1 - operint1) == 0.0 && (operand2 - operint2) == 0.0)
					{
						result = Math.pow(operint1, operint2);
						operandDeck.push(result);
					}

					else
					{
						System.out.println("ERROR");
						return ;
					}
				}
				else if (top.equals("-"))
				{
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand2 = operandDeck.pop();
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand1 = operandDeck.pop();

					result = operand1 - operand2;
					operandDeck.push(result);
				}
				else if (top.equals("%"))
				{
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand2 = operandDeck.pop();
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand1 = operandDeck.pop();

					result = operand1 % operand2;

					if (operand2 == 0)
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operandDeck.push(result);
				}
				else if (top.equals("/"))
				{
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand2 = operandDeck.pop();
					if(operandDeck.empty())
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operand1 = operandDeck.pop();

					result = operand1 / operand2;

					if (operand2 == 0)
					{
						System.out.println("ERROR");
						return ;
					}
					else
						operandDeck.push(result);
				}

			}
			else
				operandDeck.push(Double.parseDouble(top));

		}

		//postfix 출력하기

		System.out.println(printstr);


		// 결과값 출력하기
		if(!operandDeck.empty())
		{
			double finalResult = operandDeck.pop();
			System.out.println(finalResult);}
		else
		{
			System.out.println("ERROR");
			return ;
		}
	}//end calculation

}
