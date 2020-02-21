package de.TrainingsSchedule.commands.print.utility.other;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.math3.util.Precision;

import de.TrainingsSchedule.commands.print.utility.elements.chapters.Chapter;
import de.TrainingsSchedule.commands.print.utility.elements.chapters.SubChapter;
import de.TrainingsSchedule.commands.print.utility.elements.chapters.SubSubChapter;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;
import de.TrainingsSchedule.elements.specifics.Day;
import de.TrainingsSchedule.elements.specifics.Exercise;
import de.TrainingsSchedule.elements.templates.DayTemplate;
import de.TrainingsSchedule.elements.templates.PlanTemplate;
import de.TrainingsSchedule.utility.files.FileReader;
import de.TrainingsSchedule.utility.other.TimeFormat;
import de.TrainingsSchedule.utility.other.Table;

public class ChapterBuilder {

	public Chapter getChapterDescription(List<Chapter> chapters) throws FileNotFoundException {
		Chapter chapter = new Chapter(createChapterId(chapters), "Description");
		chapter.addText(FileReader.getInstance().readTxt("pdfDescription"));
		return chapter;
	}
	
	public Chapter getChapterTemplate(List<Chapter> chapters, PlanTemplate planTemplate) throws IOException {
		Chapter chapter = new Chapter(createChapterId(chapters), "Plan template");
		for(DayTemplate dayTemplate: planTemplate.getDayTemplates()) {
			SubChapter subChapter = new SubChapter(createSubChapterId(chapter), String.format("%s %d", "Day", dayTemplate.getId()));
			Table dayTemplateTable = dayTemplate.toTable();
			dayTemplateTable.setFixedColumnWidths(FileReader.getInstance().readPrp("columnwidthstemplate"));
			subChapter.addTable(dayTemplateTable, null);
			
			chapter.addSubChapter(subChapter);
		}
		return chapter;
	}
	
	public Chapter getChapterPlan(List<Chapter> chapters, List<Day> days) throws IOException {
		Chapter chapter = new Chapter(createChapterId(chapters), "Plan");

		List<Integer> sets = new ArrayList<Integer>();
		List<Integer> timeSets = new ArrayList<Integer>();
		for(Day day: days) {
			day.getExercises().stream().forEach(e -> sets.addAll(getSets(e)));
			day.getExercises().stream().forEach(e -> timeSets.addAll(getTimeSets(e)));
		}
		
		SubChapter subChapter1 = new SubChapter(createSubChapterId(chapter), "Statistics");
		Table statTable = createStatTable(
				new String[] {
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
						"Reps made"
				}, new String[] {
						days.size()+"",
						TimeFormat.minutesToHours(days.stream().mapToInt(d -> d.getTime()).sum()),
						Precision.round(days.stream().mapToDouble(d -> d.getTime()).average().getAsDouble(), 2)+" min",
						days.stream().mapToInt(d -> d.getTime()).max().getAsInt()+ " min",
						days.stream().mapToInt(d -> d.getTime()).min().getAsInt()+ " min",
						Precision.round(days.stream().mapToDouble(d -> d.getWeight()).average().getAsDouble(), 2)+" kg",
						days.stream().mapToDouble(d -> d.getWeight()).max().getAsDouble()+" kg",
						days.stream().mapToDouble(d -> d.getWeight()).min().getAsDouble()+" kg",
						sets.size()+timeSets.size()+"",
						timeSets.size()+"",
						sets.stream().mapToInt(i -> i).sum()+""}
		);
		subChapter1.addTable(statTable, null);
		chapter.addSubChapter(subChapter1);
		
		SubChapter subChapter2 = new SubChapter(createSubChapterId(chapter), "Body weight");
		List<Date> dates = days.stream().map(d -> d.getDate()).collect(Collectors.toList());
		List<Double> weights = days.stream().map(d -> d.getWeight()).collect(Collectors.toList());
		ChartBuilder chartBuilder = new ChartBuilder();
		chartBuilder.createLineChart("bodyweight", Properties.chart_1, dates, weights, "Body weight");
		subChapter2.addChart(chartBuilder.getChart(), null);
		chapter.addSubChapter(subChapter2);
		
		SubChapter subChapter3 = new SubChapter(createSubChapterId(chapter), "Training time");
		List<Double> trainingTime = days.stream().map(d -> (double)d.getTime()).collect(Collectors.toList());	
		chartBuilder.createLineChart("trainingtime", Properties.chart_1, dates, trainingTime, "Training time");
		subChapter3.addChart(chartBuilder.getChart(), null);
		chapter.addSubChapter(subChapter3);
		
		return chapter;
	}
	
	public Chapter getChapterDays(List<Chapter> chapters, PlanTemplate planTemplate, List<Day> dayList) throws IOException {
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
			SubChapter subChapter = new SubChapter(createSubChapterId(chapter), "Day "+(i+1));			
			
			SubSubChapter subSubChapter1 = new SubSubChapter(createSubSubChapterId(subChapter), "Template");
			Table dayTemplateTable = planTemplate.getDayTemplates().get(i).toTable();
			dayTemplateTable.setFixedColumnWidths(FileReader.getInstance().readPrp("columnwidthstemplate"));
			subSubChapter1.addTable(dayTemplateTable, null);
			subChapter.addSubSubChapter(subSubChapter1);
			
			SubSubChapter subSubChapter2 = new SubSubChapter(createSubSubChapterId(subChapter), "Statistics");
			Table statTable = createStatTable(
					new String[] {
							"Total days trained",
							"Total time trained", 
							"Average time trained"
					}, new String[] {
							days.get(dayId).size()+"",
							TimeFormat.minutesToHours(days.get(dayId).stream().mapToInt(d -> d.getTime()).sum()),
							Precision.round(days.get(dayId).stream().mapToInt(d -> d.getTime()).average().getAsDouble(), 2)+" min"
			});
			subSubChapter2.addTable(statTable, null);
			subChapter.addSubSubChapter(subSubChapter2); 
			
			SubSubChapter subSubChapter3 = new SubSubChapter(createSubSubChapterId(subChapter), "Days");
			List<Table> dayTables = days.get(dayId).stream().map(d -> d.toTable()).collect(Collectors.toList());
			Set<Entry<Object, Object>> fixedColumnWidths = FileReader.getInstance().readPrp("columnwidthsday");
			dayTables.stream().forEach(t -> t.setFixedColumnWidths(fixedColumnWidths));
			List<String> tableCaptions = days.get(dayId).stream().map(d -> d.toTable().getHeadline()).collect(Collectors.toList());
			subSubChapter3.setTables(dayTables, tableCaptions);
			subChapter.addSubSubChapter(subSubChapter3);
			
			chapter.addSubChapter(subChapter);
		}

		return chapter;
	}
	
	public Chapter getChapterExercises(List<Chapter> chapters, List<Day> days) throws IOException {
		Chapter chapter = new Chapter(createChapterId(chapters), "Exercises");
		
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		days.stream().forEach(d -> exerciseList.addAll(d.getExercises()));
		Map<String, List<Exercise>> exercises = new HashMap<String, List<Exercise>>();
		List<String> keys = new ArrayList<String>();
		
		for(Exercise exercise: exerciseList) {
			String key = String.format("%s: %s", exercise.getName(), exercise.getVariation());
			if(!exercises.containsKey(key)) {
				exercises.put(key, new ArrayList<Exercise>());
				keys.add(key);
			}
			exercises.get(key).add(exercise);
		}
		
		for(int i=0; i<exercises.size(); i++) {
			String key = keys.get(i);
			SubChapter subChapter = new SubChapter(createSubChapterId(chapter), key.replace(": -", ""));
			SubSubChapter subSubChapter1 = new SubSubChapter(createSubSubChapterId(subChapter), "Statistics"); 
			List<Integer> sets = new ArrayList<Integer>();
			exercises.get(key).stream().forEach(e -> sets.addAll(getSets(e)));
			List<Integer> timeSets = new ArrayList<Integer>();
			exercises.get(key).stream().forEach(e -> timeSets.addAll(getTimeSets(e)));
			Table statTable = createStatTable(
					new String[] {
						"Number of performances",
						"Peak weight",
						"Least weight",
						"Peak reps",
						"Least reps",
						"Peak timeset",
						"Least timeset"
					},new String[] {
						exercises.get(key).size()+"",
						exercises.get(key).stream().mapToDouble(e -> e.getWeight()).max().getAsDouble()+"",
						exercises.get(key).stream().mapToDouble(e -> e.getWeight()).min().getAsDouble()+"",
						Collections.max(sets)+"",
						Collections.min(sets)+"",
						timeSets.isEmpty()? "-" : TimeFormat.minutesToHours(Collections.max(timeSets)),
						timeSets.isEmpty()? "-" : TimeFormat.minutesToHours(Collections.min(timeSets)),
					});
			subSubChapter1.addTable(statTable, null);
			subChapter.addSubSubChapter(subSubChapter1);
			
			SubSubChapter subSubChapter2 = new SubSubChapter(createSubSubChapterId(subChapter), "Performances");
			List<String> header = Arrays.asList(new String[] {"Date", "ID", "Name",  "Variation", "Weight", "Reps"});
			Table performances = new Table(header);
			performances.setFixedColumnWidths(FileReader.getInstance().readPrp("columnwidthsperformance"));
			days.stream().forEach(d -> d.getExercises().stream().filter(e -> String.format("%s: %s", e.getName(), e.getVariation()).equals(key)).forEach(e -> performances.addRow(e.toRow(true))));
			subSubChapter2.addTable(performances, null);
			subChapter.addSubSubChapter(subSubChapter2);
			
			SubSubChapter subSubChapter3 = new SubSubChapter(createSubSubChapterId(subChapter), "Rep average chart");
			ChartBuilder chartBuilder = new ChartBuilder();
			List<Date> dateList = exercises.get(key).stream().map(e -> e.getDate()).collect(Collectors.toList());
			List<Double> weightList = exercises.get(key).stream().map(e -> e.getWeight()).collect(Collectors.toList());
			List<Double> setList = new ArrayList<Double>();
			exercises.get(key).stream().forEach(e -> setList.add(getSets(e).size()>0?getSets(e).stream().mapToDouble(s -> s).average().getAsDouble():null));
			if(!isNull(setList)) {
				if(weightList.stream().mapToDouble(w -> w).sum()<=0) {
					chartBuilder.createLineChart(key+" sets", Properties.chart_1, dateList, setList, "Reps");
				}else {
					chartBuilder.createLineChart(key+" sets", Properties.chart_1, dateList, setList, "Reps", weightList, "Weight");
				}
				subSubChapter3.addChart(chartBuilder.getChart(), null);
				subChapter.addSubSubChapter(subSubChapter3);
			}
			
			List<Double> timeSetList = new ArrayList<Double>();
			exercises.get(key).stream().forEach(e -> timeSetList.add(getTimeSets(e).size()>0?getTimeSets(e).stream().mapToDouble(t -> t).average().getAsDouble():null));
			if(!isNull(timeSetList)) {
				SubSubChapter subSubChapter4 = new SubSubChapter(createSubSubChapterId(subChapter), "Time average chart");
				chartBuilder.createLineChart(key+"timesets", Properties.chart_1, dateList, timeSetList, "Time average");
				subSubChapter4.addChart(chartBuilder.getChart(), null);
				subChapter.addSubSubChapter(subSubChapter4);
			}
						
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
	
	private Table createStatTable(String[] labels, String[] stats) {
		Table table = new Table();
		table.addColumn(Arrays.asList(labels));
		table.addColumn(Arrays.asList(stats));
		return table;
	}
	
	private List<Integer> getSets(Exercise exercise){
		List<Integer> sets = exercise.getReps();
		if(exercise.getTimeSets()!=null) {
			for(int i=exercise.getTimeSets().size()-1; i>=0; i--) {
				sets.remove(exercise.getTimeSets().get(i));
			}
		}
		return sets;
	}
	
	private List<Integer> getTimeSets(Exercise exercise){
		List<Integer> timeSets = new ArrayList<Integer>();
		if(exercise.getTimeSets()!=null) {				
			for(Integer timeSet: exercise.getTimeSets()) {
				timeSets.add(exercise.getReps().get(timeSet-1));
			}
		}	
		return timeSets;
	}
	
	private boolean isNull(List<Double> list) {
		for(Double d: list) {
			if(d!=null) {
				return false;
			}
		}
		return true;
	}
	
}