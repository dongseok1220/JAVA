import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.awt.event.*;

public class Calculator_20191619 {
	private static Label disp = new Label("0", Label.RIGHT);
	private static Button[] num = new Button[10];
	// private static char operator; //연산자
	// private static double result = 0;
	private static boolean check = true;
	private static boolean dotcheck = false;

	public static class WindowDestroyer extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	public static class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == ".") {
				check = false;
				dotcheck = true;
				disp.setText(disp.getText() + ".");
				return;
			}

			for (int i = 0; i < 10; i++) {
				if (e.getSource() == num[i]) {

					if (check) {
						if (disp.getText().equals("0") && num[i].getLabel().equals("0"))
							return;
						disp.setText(num[i].getLabel());
						check = false;
					} else {
						String tmp = disp.getText();
						if (dotcheck) {
							disp.setText(tmp + num[i].getLabel());
						} else {
							disp.setText(tmp + num[i].getLabel());
						}
					}
					return;
				}
			}

			if (e.getActionCommand() == "+") {
				disp.setText(disp.getText() + " + ");
				return;
			} else if (e.getActionCommand() == "-") {
				if (disp.getText() == "0") {
					check = false;
					disp.setText("-");
					return;
				}
				disp.setText(disp.getText() + " - ");
				return;
			} else if (e.getActionCommand() == "÷") {
				disp.setText(disp.getText() + " ÷ ");
				return;
			} else if (e.getActionCommand() == "X") {
				disp.setText(disp.getText() + " X ");
				return;
			}

			if (e.getActionCommand() == "AC") { // All clear
				disp.setText("0");
				check = true;
				dotcheck = false;
				return;
			}

			if (e.getActionCommand() == "=") {
				calc();
				check = true;
				dotcheck = false;
				return;
			}
			if (e.getActionCommand() == "ln") {
				check = false;
				if (disp.getText() == "0") {
					disp.setText(" ln ");
					return;
				}
				disp.setText(disp.getText() + " ln ");
				return;
			}
			if (e.getActionCommand() == "log") {
				check = false;
				if (disp.getText() == "0") {
					disp.setText(" log ");
					return;
				}
				disp.setText(disp.getText() + " log ");
				return;
			}
			if (e.getActionCommand() == "√") {
				check = false;
				if (disp.getText() == "0") {
					disp.setText(" √ ");
					return;
				}
				disp.setText(disp.getText() + " √ ");
				return;
			}
			if (e.getActionCommand() == "x!") {
				check = false;
				disp.setText(disp.getText() + " ! ");
				return;
			}
			if (e.getActionCommand() == "^") {
				check = false;
				disp.setText(disp.getText() + " ^ ");
				return;
			}
			if (e.getActionCommand() == "(") {
				check = false;
				if (disp.getText() == "0") {
					disp.setText(" ( ");
					return;
				}
				disp.setText(disp.getText() + " ( ");
				return;
			}
			if (e.getActionCommand() == ")") {
				check = false;
				if (disp.getText() == "0") {
					disp.setText(" ) ");
					return;
				}
				disp.setText(disp.getText() + " ) ");
				return;
			}
			if (e.getActionCommand() == "%") {
				check = false;
				disp.setText(disp.getText() + " % ");
				return;
			}

		}
	}

	public static int opOrder1(String op) {
		switch (op) {
		case "+":
		case "-":
			return 1;
		case "X":
		case "÷":
			return 2;
		case "(":
			return 3;
		case ")":
			return 3;
		default:
			return -1;
		}
	}

	public static int opOrder2(String op) {
		switch (op) {
		case "+":
		case "-":
			return 1;
		case "X":
		case "÷":
			return 2;
		case "(":
			return 0;
		case ")":
			return 0;
		default:
			return -1;
		}
	}

//	// 연산스택
//	static Stack<String> stack = new Stack<>();
//	// 결과
	static ArrayList<String> result = new ArrayList<>();
//	// 출력
	static ArrayList<String> output = new ArrayList<>();

	private static String Calculate(String v) {
		if (v.contains("^")) {
			String n = v.substring(0, v.indexOf("^"));
			String m = v.substring(v.indexOf("^") + 1, v.length());
			v = String.valueOf(Math.pow(Double.parseDouble(n), Double.parseDouble(m)));

		} else if (v.contains("√")) {
			String n = v.substring(0, v.indexOf("√"));
			String m = v.substring(v.indexOf("√") + 1, v.length());

			if (n.length() == 0) {
				v = String.valueOf(Math.sqrt(Double.parseDouble(m)));
			} else {
				v = String.valueOf(Math.sqrt(Double.parseDouble(m)) * Double.parseDouble(n));
			}
		} else if (v.contains("%")) {
			String n = v.substring(0, v.indexOf("%"));
			String m = v.substring(v.indexOf("%") + 1, v.length());
			if (m.length() == 0) {
				v = String.valueOf(0.01 * Double.parseDouble(n));
			} else {
				v = String.valueOf(0.01 * Double.parseDouble(n) * Double.parseDouble(m));
			}

		} else if (v.contains("log")) {
			String n = v.substring(v.indexOf("g") + 1, v.length());
			String m = v.substring(0, v.indexOf("l"));
			double k = Double.parseDouble(n);
			if (m.length() == 0 ) {
				v = String.valueOf(Math.log10(k));
			}
			else {
				v = String.valueOf(Math.log10(k) * Double.parseDouble(m));
			}

		} else if (v.contains("ln")) {
			String n = v.substring(v.indexOf("n") + 1, v.length());
			String m = v.substring(0, v.indexOf("l"));
			double k = Double.parseDouble(n);
			if (m.length() == 0 ) {
				v = String.valueOf(Math.log(k));
			}
			else {
				v = String.valueOf(Math.log(k) * Double.parseDouble(m));
			}

		} else if (v.contains("!")) {
			String n = v.substring(0, v.indexOf("!"));
			double k = Double.parseDouble(n);
			double ans = 1;
			if (k < 0) {
				k = -k;
				for (int i = 1; i <= k; i++) {
					ans *= i;
				}
				ans = -ans;
			}
			else {
				for (int i = 1; i <= k; i++) {
					ans *= i;
				}
			}
			v = String.valueOf(ans);
		}

		return v;
	}

	public static void calc() {

		result.clear();
		output.clear();

		StringBuffer buffer = new StringBuffer();
		String[] expression = disp.getText().split(" ");
		int check = 0;

		for (int i = 0; i < expression.length; i++) {
			String c = expression[i];

			if ("+".equals(c) || "-".equals(c) || "X".equals(c) || "÷".equals(c)) {
				if (check == 0) {
					String v = Calculate(buffer.toString());
					result.add(v);
				}
				if (check == 1)
					check = 0;
				result.add(c);
				buffer.setLength(0);
			} else if ("(".equals(c)) {
				result.add(c);
			} else if (")".equals(c)) {
				String v = Calculate(buffer.toString());
				check = 1;
				result.add(v);
				result.add(c);
			} else {
				buffer.append(c);
			}
		}

		if (buffer.length() != 0) {
			String v = Calculate(buffer.toString());
			result.add(v);
			buffer.setLength(0);
		}
		boolean isBracket = false;
		if (result.get(0) == "(") {
			isBracket = true;
		}
		System.out.println(result);
		StringTokenizer st = new StringTokenizer(String.join(" ", result));

		ArrayList<String> sb = new ArrayList<>();
		Stack<String> stack = new Stack<>();

		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			switch (token) {
			case "+":
			case "-":
			case "X":
			case "÷":
				while (!stack.isEmpty() && (opOrder2(stack.peek()) >= opOrder1(token))) {
					System.out.println(stack.peek());
					sb.add(stack.pop());
				}
				stack.push(token);
				break;
			case "(":
				stack.push(token);
				break;
			case ")":
				while (!stack.isEmpty() && !stack.peek().equals("(")) {
					sb.add(stack.pop());
				}
				stack.pop();
				break;
			default:
				sb.add(token);
			}
//			if (!stack.isEmpty())
//				System.out.println("stack :" + stack.peek());
//			System.out.println(sb);
		}

		while (!stack.isEmpty()) {
			sb.add(stack.pop());
		}
		if (isBracket) {
			sb.remove(0);
		}

		String[] result = new String[sb.size()];

		for (int i = 0; i < sb.size(); i++) {
			result[i] = sb.get(i);
//			System.out.println(result[i]);
		}

		resultPrint(result);

	}

	public static void resultPrint(String[] str) {

		Stack<Double> stack2 = new Stack<Double>();

		for (String x : str) {

			if (!x.equals("+") && !x.equals("-") && !x.equals("X") && !x.equals("÷")) {
				stack2.push(Double.parseDouble(x));
			} else {

				Double a = stack2.pop();
				Double b = stack2.pop();

				switch (x) {
				case "+":
					stack2.push(b + a);
					break;
				case "-":
					stack2.push(b - a);
					break;
				case "X":
					stack2.push(b * a);
					break;
				case "÷":
					stack2.push(b / a);
					break;
				}
			}
		}
		Double s = stack2.pop();
		DecimalFormat df = new DecimalFormat("#.###############");
		disp.setText(df.format(s));
	}

	public static void main(String[] args) {

		Frame f = new Frame("Calculator");

		Panel main = new Panel(new BorderLayout(3, 3));
		main.add("North", disp);

		Panel first = new Panel(new GridLayout(1, 5));

		Button fac = new Button("x!");
		Button left_parentheses = new Button("(");
		Button right_parentheses = new Button(")");
		Button remain = new Button("%");
		Button AC = new Button("AC");

		Panel second = new Panel(new GridLayout(1, 5));

		Button ln = new Button("ln");
		num[7] = new Button("7");
		num[8] = new Button("8");
		num[9] = new Button("9");
		Button divide = new Button("÷");

		Panel third = new Panel(new GridLayout(1, 5));

		Button log = new Button("log");
		num[4] = new Button("4");
		num[5] = new Button("5");
		num[6] = new Button("6");
		Button multi = new Button("X");

		Panel fourth = new Panel(new GridLayout(1, 5));

		Button root = new Button("√");
		num[1] = new Button("1");
		num[2] = new Button("2");
		num[3] = new Button("3");
		Button minus = new Button("-");

		Panel fifth = new Panel(new GridLayout(1, 5));

		Button pow = new Button("^");
		num[0] = new Button("0");
		Button dot = new Button(".");
		Button equal = new Button("=");
		Button plus = new Button("+");

		fac.addActionListener(new ButtonHandler());
		right_parentheses.addActionListener(new ButtonHandler());
		left_parentheses.addActionListener(new ButtonHandler());
		remain.addActionListener(new ButtonHandler());
		AC.addActionListener(new ButtonHandler());

		first.add(fac);
		first.add(left_parentheses);
		first.add(right_parentheses);
		first.add(remain);
		first.add(AC);

		ln.addActionListener(new ButtonHandler());
		num[7].addActionListener(new ButtonHandler());
		num[8].addActionListener(new ButtonHandler());
		num[9].addActionListener(new ButtonHandler());
		divide.addActionListener(new ButtonHandler());

		second.add(ln);
		second.add(num[7]);
		second.add(num[8]);
		second.add(num[9]);
		second.add(divide);

		log.addActionListener(new ButtonHandler());
		num[4].addActionListener(new ButtonHandler());
		num[5].addActionListener(new ButtonHandler());
		num[6].addActionListener(new ButtonHandler());
		multi.addActionListener(new ButtonHandler());

		third.add(log);
		third.add(num[4]);
		third.add(num[5]);
		third.add(num[6]);
		third.add(multi);

		root.addActionListener(new ButtonHandler());
		num[1].addActionListener(new ButtonHandler());
		num[2].addActionListener(new ButtonHandler());
		num[3].addActionListener(new ButtonHandler());
		minus.addActionListener(new ButtonHandler());

		fourth.add(root);
		fourth.add(num[1]);
		fourth.add(num[2]);
		fourth.add(num[3]);
		fourth.add(minus);

		pow.addActionListener(new ButtonHandler());
		num[0].addActionListener(new ButtonHandler());
		dot.addActionListener(new ButtonHandler());
		equal.addActionListener(new ButtonHandler());
		plus.addActionListener(new ButtonHandler());

		fifth.add(pow);
		fifth.add(num[0]);
		fifth.add(dot);
		fifth.add(equal);
		fifth.add(plus);

		Panel temp = new Panel(new GridLayout(5, 5));
		temp.add(first);
		temp.add(second);
		temp.add(third);
		temp.add(fourth);
		temp.add(fifth);

		main.add(temp);

		f.add("Center", main);
		f.pack();

		f.setSize(300, 200);
		f.setVisible(true);

		WindowDestroyer listener = new WindowDestroyer(); // window destroy button
		f.addWindowListener(listener);
	}

}
