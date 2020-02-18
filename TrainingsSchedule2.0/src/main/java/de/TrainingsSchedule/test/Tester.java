package de.TrainingsSchedule.test;

import de.TrainingsSchedule.commands.print.utility.elements.Chapter;

public class Tester {
	
	public static void main(String[] args)  {
		Chapter chapter = new Chapter(1, null, null, null, null, null);
		System.out.println(createSubChapterId(chapter));
	}
	private static double createSubChapterId(Chapter chapter) {
		System.out.println(((double)chapter.getSubChapters().size()+1)/10);
		return chapter.getId()+((double)(chapter.getSubChapters().size()+1)/10);
	}	

}