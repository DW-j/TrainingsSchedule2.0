package de.TrainingsSchedule.commands.print.utility.other;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.util.Precision;

import de.TrainingsSchedule.commands.print.utility.elements.chapters.Chapter;
import de.TrainingsSchedule.commands.print.utility.elements.chapters.SubChapter;
import de.TrainingsSchedule.commands.print.utility.elements.chapters.SubSubChapter;
import de.TrainingsSchedule.elements.specifics.Day;
import de.TrainingsSchedule.elements.specifics.Exercise;
import de.TrainingsSchedule.elements.templates.DayTemplate;
import de.TrainingsSchedule.elements.templates.PlanTemplate;
import de.TrainingsSchedule.utility.files.FileReader;
import de.TrainingsSchedule.utility.other.Constants;
import de.TrainingsSchedule.utility.other.Table;

public class ChapterBuilder {

	public Chapter getChapterDescription(List<Chapter> chapters) throws FileNotFoundException {
		Chapter chapter = new Chapter(createChapterId(chapters), "Description");
		chapter.addText(FileReader.getInstance().readTxt("pdfDescription"));
		return chapter;
	}
	
	public Chapter getChapterTemplate(List<Chapter> chapters, PlanTemplate planTemplate) {
		Chapter chapter = new Chapter(createChapterId(chapters), "Plan template");
		for(DayTemplate dayTemplate: planTemplate.getDayTemplates()) {
			SubChapter subChapter = new SubChapter(createSubChapterId(chapter), String.format("%s %d", "Day", dayTemplate.getId()));
			subChapter.addTable(dayTemplate.toTable(), null);
			chapter.addSubChapter(subChapter);
		}
		return chapter;
	}
	
	public Chapter getChapterPlan(List<Chapter> chapters, List<Day> days) {
		Chapter chapter = new Chapter(createChapterId(chapters), "Plan");
		Table statTable = new Table();
		List<Integer> sets = new ArrayList<Integer>();
		List<Integer> timeSets = new ArrayList<Integer>();
		for(Day day: days) {
			for(Exercise exercise: day.getExercises()) {
				List<Integer> reps = exercise.getReps();
				if(exercise.getTimeSets()!=null) {
					timeSets.addAll(exercise.getTimeSets());
					for(int i=exercise.getTimeSets().size()-1; i>=0; i--) {
						reps.remove(exercise.getTimeSets().get(i));
					}
				}
				sets.addAll(reps);
			}
		}
		List<String> statColumn1 = Arrays.asList(new String[] {
				"Total days trained",
				"Total time trained",
				"Average time trained",
				"Peek training time", 
				"Least training time", 
				"Average body weight", 
				"Peek body weight", 
				"Least body weight",
				"Sets made",
				"Time sets made",
				"Reps made"});
		List<String> statColumn2 = Arrays.asList(new String[] {
				days.size()+"",
				Constants.getDurationhoursformat().format(days.stream().mapToInt(d -> d.getTime()).sum()*60000)+" h",
				Precision.round(days.stream().mapToInt(d -> d.getTime()).average().getAsDouble(), 2)+" min",
				days.stream().mapToInt(d -> d.getTime()).max().getAsInt()+ " min",
				days.stream().mapToInt(d -> d.getTime()).min().getAsInt()+ " min",
				Precision.round(days.stream().mapToDouble(d -> d.getWeight()).average().getAsDouble(), 2)+" kg",
				days.stream().mapToDouble(d -> d.getWeight()).max().getAsDouble()+" kg",
				days.stream().mapToDouble(d -> d.getWeight()).min().getAsDouble()+" kg",
				sets.size()+timeSets.size()+"",
				timeSets.size()+"",
				sets.stream().mapToInt(i -> i).sum()+""
			});
		statTable.addColumn(statColumn1);
		statTable.addColumn(statColumn2);
		SubChapter subChapter = new SubChapter(createSubChapterId(chapter), "Statistics");
		subChapter.addTable(statTable, null);
		chapter.addSubChapter(subChapter);
		return chapter;
	}
	
	public Chapter getChapterDays(List<Chapter> chapters, PlanTemplate planTemplate, List<Day> dayList) {
		Map<Integer, List<Day>> days = new HashMap<Integer, List<Day>>();
		for(Day day: dayList) {
			if(!days.containsKey(day.getId())) {
				days.put(day.getId(), new ArrayList<Day>());
			}
			days.get(day.getId()).add(day);
		}
		
		Chapter chapter = new Chapter(createChapterId(chapters), "Days");
		
		for(int i=0; i<days.size(); i++) {
			int dayId = i+1;
			SubChapter subChapter = new SubChapter(createSubChapterId(chapter), "Day "+i+1);			
			
			SubSubChapter subSubChapter1 = new SubSubChapter(createSubSubChapterId(subChapter), "Template");
			subSubChapter1.addTable(planTemplate.getDayTemplates().get(i).toTable(), null);
			subChapter.addSubSubChapter(subSubChapter1);
			
			SubSubChapter subSubChapter2 = new SubSubChapter(createSubSubChapterId(subChapter), "Statistics");
			Table statTable = new Table();
			List<String> statColumn1 = Arrays.asList(new String[] {"Total days trained", "Total time trained", "Average time trained"});
			List<String> statColumn2 = Arrays.asList(new String[] {
					days.get(dayId).size()+"",
					Constants.getDurationhoursformat().format(days.get(dayId).stream().mapToInt(d -> d.getTime()).sum()*60000)+" h",
					days.get(dayId).stream().mapToInt(d -> d.getTime()).sum()/days.size()+" min"
				});
			statTable.addColumn(statColumn1);
			statTable.addColumn(statColumn2);
			subSubChapter2.addTable(statTable, null);
			subChapter.addSubSubChapter(subSubChapter2); 
			
			SubSubChapter subSubChapter3 = new SubSubChapter(createSubSubChapterId(subChapter), "Days");
			List<Table> dayTables = days.get(dayId).stream().map(d -> d.toTable()).collect(Collectors.toList());
			List<String> tableCaptions = days.get(dayId).stream().map(d -> d.toTable().getHeadline()).collect(Collectors.toList());
			subSubChapter3.setTables(dayTables, tableCaptions);
			subChapter.addSubSubChapter(subSubChapter3);
			
			chapter.addSubChapter(subChapter);
		}

		return chapter;
	}
	
	public Chapter getChapterExercises(List<Chapter> chapters, List<Day> days) {
		Chapter chapter = new Chapter(createChapterId(chapters), "Exercises");
		
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		days.stream().forEach(d -> exerciseList.addAll(d.getExercises()));
		Map<String, List<Exercise>> exercises = new HashMap<String, List<Exercise>>();
		List<String> keys = new ArrayList<String>();
		
		for(Exercise exercise: exerciseList) {
			String temp = exercise.getName();
			if(!exercise.getVariation().equals("-")) {
				temp += ", "+exercise.getVariation();
			}
			if(!exercises.containsKey(temp)) {
				exercises.put(temp, new ArrayList<Exercise>());
				keys.add(temp);
			}
			exercises.get(temp).add(exercise);
		}
		
		for(int i=0; i<exercises.size(); i++) {
			SubChapter subChapter = new SubChapter(createSubChapterId(chapter), keys.get(i));
			SubSubChapter subSubChapter1 = new SubSubChapter(createSubSubChapterId(subChapter), "Statistics"); 
			subChapter.addSubSubChapter(subSubChapter1);
			SubSubChapter subSubChapter2 = new SubSubChapter(createSubSubChapterId(subChapter), "Performances");
			subChapter.addSubSubChapter(subSubChapter2);


			//			List<String> header = Arrays.asList(new String[] {"Date", "ID", "Name",  "Variation", "Weight", "Reps"});
//			Table performances = new Table(header);
//			for(Exercise exercise: exercises.get(keys.get(i))) {
//				performances.addRow(exercise.);
			//} //exercise -> date
			
			chapter.addSubChapter(subChapter);
		}
		
		return chapter;
	}
	
	private String createChapterId(List<Chapter> chapters) {
		return (chapters.size()+1)+"";
	}

	
	private String createSubChapterId(Chapter chapter) {
		if(chapter.getSubChapters()==null) {
			return chapter.getId() + "." + 1;
		}
		return chapter.getId() + '.' + (chapter.getSubChapters().size()+1);
	}
	
	private String createSubSubChapterId(SubChapter subChapter) {
		if(subChapter.getSubSubChapters()==null) {
			return subChapter.getId() + "." + 1;
		}
		return subChapter.getId() + "." + (subChapter.getSubSubChapters().size()+1);
	}
	
}