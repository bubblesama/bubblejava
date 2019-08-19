package com.bubblebob.tool.parsing;

public class TestParser extends AbstractParser{

	public TestParser(String fileName) {
		super(fileName);
	}

	@Override
	public boolean initParsing() throws InvalidParsingFormatException,UnknownParsingException, InitParsingException {
		System.out.println("initParsing");
		return true;
	}

	@Override
	public boolean readLine(String line) throws InvalidParsingFormatException,UnknownParsingException {
		System.out.println("line: ("+getIndex()+"): "+line);
		return true;
	}

	@Override
	public boolean finalizeParsing() throws InvalidParsingFormatException,UnknownParsingException {
		System.out.println("finalizeParsing");
		return true;
	}

	public static void main(String[] args) {
		TestParser parser = new TestParser("C:/Paul/workspace/tools/resources/parsing-test.txt");
		try {
			parser.parse();
		} catch (ParsingException e) {
			e.printStackTrace();
		}
	}
}
