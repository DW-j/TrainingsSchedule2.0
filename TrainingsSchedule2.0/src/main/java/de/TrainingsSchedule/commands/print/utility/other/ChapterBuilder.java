package de.TrainingsSchedule.commands.print.utility.other;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.TrainingsSchedule.commands.print.utility.elements.Chapter;
import de.TrainingsSchedule.commands.print.utility.elements.SubChapter;
import de.TrainingsSchedule.elements.specifics.Day;
import de.TrainingsSchedule.elements.templates.PlanTemplate;
import de.TrainingsSchedule.utility.files.FileReader;
import de.TrainingsSchedule.utility.other.Constants;
import de.TrainingsSchedule.utility.other.Table;

public class ChapterBuilder {

	public Chapter getChapterDescription(List<Chapter> chapters) throws FileNotFoundException {
		Chapter chapter = new Chapter(createChapterId(chapters), "Description", FileReader.getInstance().readTxt("pdfDescription"), null, null, null);
		return chapter;
	}
	
	public Chapter getChapterTemplate(List<Chapter> chapters, PlanTemplate planTemplate) {
		Chapter chapter = new Chapter(createChapterId(chapters), "Plan template", null, null, null, null);
		planTemplate.getDayTemplates().stream().forEach(d -> chapter.addSubChapter(new SubChapter(String.format("%s %d", "Day", d.getId()), createSubChapterId(chapter), null, d.toTable(), null, null)));		
		return chapter;
	}
	
	public Chapter getChapterPlan(List<Chapter> chapters, List<Day> days) {
		Chapter chapter = new Chapter(createChapterId(chapters), "Plan", null, null, null, null);
		Table statTable = new Table();
		List<String> statColumn1 = Arrays.asList(new String[] {"Total days trained", "Total time trained", "Average time trained"});
		List<String> statColumn2 = Arrays.asList(new String[] {
				days.size()+"",
				Constants.getDurationhoursformat().format(days.stream().mapToInt(d -> d.getTime()).sum()*60000)+" h",
				days.stream().mapToInt(d -> d.getTime()).sum()/days.size()+" min"
			});
		statTable.addColumn(statColumn1);
		statTable.addColumn(statColumn2);
		SubChapter subChapter1 = new SubChapter("Statistics", createSubChapterId(chapter), null, statTable, null, null);
		chapter.addSubChapter(subChapter1);
		return chapter;
	}
	
	public List<Chapter> getChaptersDays(List<Chapter> chapters, PlanTemplate planTemplate, List<Day> dayList) {
		Map<Integer, List<Day>> days = new HashMap<Integer, List<Day>>();
		for(Day day: dayList) {
			if(!days.containsKey(day.getId())) {
				days.put(day.getId(), new ArrayList<Day>());
			}
			days.get(day.getId()).add(day);
		}
		
		List<Chapter> newChapters = new ArrayList<Chapter>();
		
		for(int i=0; i<days.size(); i++) {
			Chapter chapter = new Chapter(createChapterId(chapters, i), String.format("Day %d", i+1), null, null, null, null);
			
			SubChapter subChapterTemplate = new SubChapter("Template", createSubChapterId(chapter), null, planTemplate.getDayTemplates().get(i).toTable(), null, null);
			chapter.addSubChapter(subChapterTemplate);
			
			newChapters.add(chapter);
		}
		
		return newChapters;
	}	
	
	private int createChapterId(List<Chapter> chapters) {
		return chapters.size()+1;
	}
	
	private int createChapterId(List<Chapter> chapters, int additionalChapters) {
		return chapters.size()+additionalChapters+1;
	}
	
	private double createSubChapterId(Chapter chapter) {
		return chapter.getId()+((double)(chapter.getSubChapters().size()+1)/10);
	}
	
}