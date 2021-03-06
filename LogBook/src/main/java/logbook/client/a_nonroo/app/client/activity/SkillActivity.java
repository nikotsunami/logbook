package logbook.client.a_nonroo.app.client.activity;

import java.util.List;

import logbook.client.ApplicationLoadingScreenEvent;
import logbook.client.ApplicationLoadingScreenHandler;
import logbook.client.a_nonroo.app.client.SkillFilteredResultProxy;
import logbook.client.a_nonroo.app.client.place.SkillPlace;
import logbook.client.a_nonroo.app.client.ui.SkillLevelCheckboxView;
import logbook.client.a_nonroo.app.client.ui.SkillLevelCheckboxViewImpl;
import logbook.client.a_nonroo.app.client.ui.SkillLevelIconButtonView;
import logbook.client.a_nonroo.app.client.ui.SkillLevelIconButtonViewImpl;
import logbook.client.a_nonroo.app.client.ui.SkillLevelTextAreaViewImpl;
import logbook.client.a_nonroo.app.client.ui.SkillView;
import logbook.client.a_nonroo.app.client.ui.SkillViewImpl;
import logbook.client.a_nonroo.app.client.ui.custom.widget.CustomProgressbar;
import logbook.client.a_nonroo.app.request.LogBookRequestFactory;
import logbook.client.managed.proxy.ClassificationTopicProxy;
import logbook.client.managed.proxy.MainClassificationProxy;
import logbook.client.managed.proxy.SkillProxy;
import logbook.client.managed.proxy.StudentProxy;
import logbook.client.managed.proxy.TopicProxy;
import logbook.client.style.widgetsnewcustomsuggestbox.test.client.ui.widget.suggest.impl.simple.DefaultSuggestOracle;
import logbook.shared.i18n.LogBookConstants;
import logbook.shared.scaffold.LogBookConstant;
import logbook.shared.util.UtilityLogBook;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author Manish
 * 
 */
public class SkillActivity extends AbstractActivity implements
		SkillView.presenter, SkillView.Delegate,
		SkillLevelCheckboxView.presenter,SkillLevelCheckboxView.Delegate,
		SkillLevelIconButtonView.presenter,SkillLevelIconButtonView.Delegate{

	private LogBookRequestFactory requests;
	private PlaceController placeController;
	private AcceptsOneWidget widget;
	private SkillView view;
	private SkillLevelCheckboxView checkBoxview;
	 private Long mainClassificationId =null;
	 private Long classificaitonTopicId = null;
	 private Long topicId =null;
	 private SkillView systemStartView;
	private SkillActivity skillActivity;
	private SkillPlace place;
	private int tabIndex = 0;
	private Timer errorMessageTimer;

	private static StudentProxy currentStudent=null;
	
	private Boolean result;
	private ActivityManager activityManager;
	private FlexTable skillFlexTable;
	LogBookConstants constants = GWT.create(LogBookConstants.class);

	public HandlerManager handlerManager;// = new HandlerManager(this);
	// private HandlerRegistration rangeChangeHandler;

	public int currenttab = 0;

	public SkillActivity(LogBookRequestFactory requests,
			PlaceController placeController, SkillPlace skillPlace) {
		Log.info("Call Activity Login");
		this.requests = requests;
		this.placeController = placeController;
		this.place = skillPlace;
		this.handlerManager = skillPlace.handler;

		skillActivity = this;

	}

	/*
	 * public void addSelectChangeHandler(SelectChangeHandler handler) {
	 * handlerManager.addHandler(SelectChangeEvent.getType(), handler);
	 * 
	 * }
	 */
	
	static class  ShowCriteria{
		
		
		static MainClassificationProxy mProxy=null;
		static ClassificationTopicProxy ctProxy=null;
		static TopicProxy tProxy=null;
		static String fullTextSearch="";
		static String chkAsc=new Integer(0).toString();
		
	}
	
	public SkillActivity(LogBookRequestFactory requests,
			PlaceController placeController) {

		this.requests = requests;
		this.placeController = placeController;
		
		initLoading();
	}

	public void onStop() {
		mainClassificationId=null;
		classificaitonTopicId=null;
		topicId=null;
		view.getMainClassificationSuggestBox().setSelected(null);
		view.getClassificationTopicSuggestBox().setSelected(null);
		view.getTopicSuggestBox().setSelected(null);
		ShowCriteria.mProxy=null;
		ShowCriteria.ctProxy=null;
		ShowCriteria.tProxy=null;
		ShowCriteria.fullTextSearch="";
		ShowCriteria.chkAsc=new Integer(0).toString();
		widget.setWidget(null);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		Log.info("SystemStartActivity.start()");
		
		this.widget = panel;
		systemStartView = new SkillViewImpl();
		systemStartView.setSkillActivity(this);
		this.view = systemStartView;
		view.setDelegate(this);
		
		widget.setWidget(systemStartView.asWidget());
		
		systemStartView.asWidget().getElement().getParentElement().getParentElement().getStyle().setPosition(Position.RELATIVE);
		
		checkBoxview=new SkillLevelCheckboxViewImpl();
		checkBoxview.setDelegate(this);
		init();
		errorMessageTimer =new Timer() {
				
				@Override
				public void run() {
					view.getHpErrorMessage().setVisible(false);
									
				}
			};
			errorMessageTimer.scheduleRepeating((int)(LogBookConstant.ERROR_MESSAGE_TIME));
		
	}

	private void init() {
		/*systemStartView = new SkillViewImpl();*/
		/*checkBoxview=new SkillLevelCheckboxViewImpl();*/
		systemStartView.setPresenter(this);
		checkBoxview.setPresenter(this);
		/*this.view = systemStartView;

		widget.setWidget(systemStartView.asWidget());*/
		/*view.setDelegate(this);
		checkBoxview.setDelegate(this);*/
		
	//	findCurrentStudent();
		
		requests.studentRequestNonRoo().findStudentFromSession().fire(new Receiver<StudentProxy>() {

			@Override
			public void onSuccess(StudentProxy response) {
				if(response !=null)
				view.setStudent(response);
				
				initAllSkillSuggestions();
				
				initSkillFlexTable();
				
				view.setIsAsc(0);
				
				initSkillTableData(response,view.getIsAsc());
			}
		});
		
		/*initAllSkillSuggestions();
		
		initSkillFlexTable();
		
		initSkillTableData();*/
		
		
		

	}
	private void initLoading(){
		ApplicationLoadingScreenEvent.initialCounter();
		ApplicationLoadingScreenEvent.register(requests.getEventBus(),
				new ApplicationLoadingScreenHandler() {
					@Override
					public void onEventReceived(
							ApplicationLoadingScreenEvent event) {
//						Log.info("ApplicationLoadingScreenEvent onEventReceived Called");
						event.display();
					}
				});

	}
	/*private void findCurrentStudent() {
		
		requests.studentRequestNonRoo().findStudentFromSession().fire(new Receiver<StudentProxy>() {

			@Override
			public void onSuccess(StudentProxy response) {
				if(response !=null)
				currentStudent=response;
			}
		});
	}*/
	
	
	//chkAsc: 0=Sort Ascending ShortCut
	//chkAsc: 1=Sort Descending ShortCut
	private void initSkillTableData(StudentProxy student,int chkAsc) {
		
		showApplicationLoading(true);
		skillFlexTable.removeAllRows();
		
		Log.info("student is :" + student.getId());
		
		String fullTextSearchString=view.getFullTextSearchBox().getValue();
		
		Log.info("full text : " + fullTextSearchString);
		
	
	
		requests.skillRequestNonRoo().findSkillBySearchCriteria(view.getPager().getStart(), view.getPager().getLength(),student.getId(),mainClassificationId, classificaitonTopicId, topicId,fullTextSearchString,chkAsc).with("skillList.topic","skillList.skillComment","skillList.topic.classificationTopic","skillList.topic.classificationTopic.mainClassification","skillList.skillLevel").fire(new Receiver<SkillFilteredResultProxy>() {

			@Override
			public void onSuccess(SkillFilteredResultProxy response) {
			/*for(SkillProxy skill : response){
				System.out.println("id : " + skill.getId());
			}*/
				showApplicationLoading(false);	
			view.getPager().setRowCount(response.getTotalSkill());
			view.createHeader(view.getSkillFlexTable());
			if(response.getTotalSkill()==0){
			
			view.getHpErrorMessage().setVisible(true);
			view.getLblErrorMessage().setInnerHTML(constants.nodataFound());	
			}else{
				view.setSource(response);
			}
		}

		
	});
		
	}

	
	private void onRangeChanged(StudentProxy student,int chkAsc) {
		
		
		skillFlexTable.removeAllRows();
		
		Log.info("student is :" + student.getId());
		
		String fullTextSearchString=view.getFullTextSearchBox().getValue();
		
		Log.info("full text : " + fullTextSearchString);
		
	
	
		requests.skillRequestNonRoo().findSkillBySearchCriteria(view.getPager().getStart(), view.getPager().getLength(),student.getId(),mainClassificationId, classificaitonTopicId, topicId,fullTextSearchString,chkAsc).with("skillList.topic","skillList.topic.classificationTopic","skillList.topic.classificationTopic.mainClassification","skillList.skillLevel").fire(new Receiver<SkillFilteredResultProxy>() {

			@Override
			public void onSuccess(SkillFilteredResultProxy response) {
			/*for(SkillProxy skill : response){
				System.out.println("id : " + skill.getId());
			}*/
			//view.getPager().setRowCount(response.getTotalSkill());
			view.createHeader(view.getSkillFlexTable());
			view.setSource(response);

		}

		
	});
		
	}

	private void initAllSkillSuggestions() {
		
		initMainClassificationSuggestion();
		initClassificationTopicSuggestion(mainClassificationId);
		initTopicSuggestion(classificaitonTopicId);
		
	}
private void initMainClassificationSuggestion() {
		
		requests.mainClassificationRequest().findAllMainClassifications().with("classificationTopics","classificationTopics.topics","topics.skills","skills.skillsAcquired").fire(new Receiver<List<MainClassificationProxy>>() {

			@Override
			public void onSuccess(List<MainClassificationProxy> response) {
				
				DefaultSuggestOracle<MainClassificationProxy> suggestOracle1 = (DefaultSuggestOracle<MainClassificationProxy>) view.getMainClassificationSuggestBox().getSuggestOracle();
				suggestOracle1.setPossiblilities(response);
				view.getMainClassificationSuggestBox().setSuggestOracle(suggestOracle1);
				
				view.getMainClassificationSuggestBox().setRenderer(new AbstractRenderer<MainClassificationProxy>() {

					@Override
					public String render(MainClassificationProxy object) {
						
						if (object != null)
							return (object.getDescription());
						else
							return "";
					}
				});
			}
		});
		
	}
	
	private void initClassificationTopicSuggestion(Long mainClassificationId) {
		
		requests.classificationTopicRequestNonRoo().findClassiTopicByMainClassfication(mainClassificationId).fire(new Receiver<List<ClassificationTopicProxy>>() {

			@Override
			public void onSuccess(List<ClassificationTopicProxy> response) {
				
				DefaultSuggestOracle<ClassificationTopicProxy> suggestOracle = (DefaultSuggestOracle<ClassificationTopicProxy>) view.getClassificationTopicSuggestBox().getSuggestOracle();
				suggestOracle.setPossiblilities(response);
				view.getClassificationTopicSuggestBox().setSuggestOracle(suggestOracle);
				
				view.getClassificationTopicSuggestBox().setRenderer(new AbstractRenderer<ClassificationTopicProxy>() {

					@Override
					public String render(ClassificationTopicProxy object) {
						if (object != null)
							return (object.getDescription());
						else
							return "";
					}
				});
				
			}
		});
	}
private void initTopicSuggestion(Long classificaitonTopicId) {
		
	requests.topicRequestNonRoo().findTopicByClassficationId(classificaitonTopicId).fire(new Receiver<List<TopicProxy>>() {

		@Override
		public void onSuccess(List<TopicProxy> response) {
			
			DefaultSuggestOracle<TopicProxy> suggestOracle = (DefaultSuggestOracle<TopicProxy>) view.getTopicSuggestBox().getSuggestOracle();
			suggestOracle.setPossiblilities(response);
			view.getTopicSuggestBox().setSuggestOracle(suggestOracle);
			
			view.getTopicSuggestBox().setRenderer(new AbstractRenderer<TopicProxy>() {

				@Override
				public String render(TopicProxy object) {
					if (object != null)
						return object.getTopicDescription();
					else
						return "";
				}
			});
			
		}
	});
		
	}
	

	private void initSkillFlexTable() {
		
		skillFlexTable=view.getSkillFlexTable();
		
		/*skillFlexTable.setText(1, 0,"This is sub title of table");
		skillFlexTable.getFlexCellFormatter().setColSpan(1, 0, 6);*/
		
	/*	skillFlexTable.setHTML(2, 0," ");
		skillFlexTable.setHTML(2, 1, "Hello1");
		skillFlexTable.setHTML(2, 2, "S2");
		skillFlexTable.setWidget(2, 3, new CheckBox());
		skillFlexTable.setWidget(2, 4, new CheckBox());*/
		
		
	}

	@Override
	public void goTo(Place place) {

	}

	@Override
	public void mainClassificationSuggestboxChanged(Long mainClassificationId) {
		//this.mainClassificationId = mainClassificationId;
		
		if(mainClassificationId==null || view.getMainClassificationSuggestBox().getTextField().advancedTextBox.getText()==""){
			view.getClassificationTopicSuggestBox().setEnabled(false);
			view.getTopicSuggestBox().setEnabled(false);
			//System.out.println("Data " + view.getMainClassificationSuggestBox().getTextField().advancedTextBox.getText());
			//view.getMainClassificationSuggestBox().getTextField().advancedTextBox.setText(view.getMainClassificationSuggestBox().getTextField().advancedTextBox.getText());
			//view.getMainClassificationSuggestBox().getTextField().advancedTextBox.setText("");
			//view.getMainClassificationSuggestBox().setSelected(null);
			//view.setDefaultMessageOfSuggestionbox();
		}
		else{
			view.getClassificationTopicSuggestBox().setEnabled(true);
			view.getTopicSuggestBox().setEnabled(false);
		}
			
		view.getClassificationTopicSuggestBox().setSelected(null);
		classificaitonTopicId = null;
		
		view.getTopicSuggestBox().setSelected(null);
		topicId = null;
		
		//view.getClassificationTopicSuggestBox().getTextField().advancedTextBox.setText(constants.allClassificationTopics());
		//view.getTopicSuggestBox().getTextField().advancedTextBox.setText(constants.allTopics());
		view.setDefaultMessageOfSuggestionbox();
		
		initClassificationTopicSuggestion(mainClassificationId);
		
		initTopicSuggestion(classificaitonTopicId);
		
		//initSkillTableData();
		
	}

	@Override
	public void classificationTopicSuggestboxChanged(Long classificationId) {
		//this.classificaitonTopicId = classificationId;
		
		if(classificationId==null || view.getClassificationTopicSuggestBox().getTextField().advancedTextBox.getText()==""){
			view.getTopicSuggestBox().setEnabled(false);
		}
		else{
			view.getTopicSuggestBox().setEnabled(true);
		}
		view.getTopicSuggestBox().setSelected(null);
		topicId = null;
		
		//view.getTopicSuggestBox().getTextField().advancedTextBox.setText(constants.allTopics());
		view.setDefaultMessageOfSuggestionbox();
		
		initTopicSuggestion(classificationId);
		//initSkillTableData();
		
	}

	@Override
	public void topicSuggestboxChanged(Long topicId) {
		//this.topicId=topicId;
		/*if(topicId==null)
		view.getTopicSuggestBox().setEnabled(false);
		else
		view.getTopicSuggestBox().setEnabled(true);*/
		view.setDefaultMessageOfSuggestionbox();
		//initSkillTableData();
		
	}

	@Override
	public void resetButtonClicked() {
		
		view.getHpErrorMessage().setVisible(false);
		this.mainClassificationId=null;
		this.classificaitonTopicId=null;
		this.topicId=null;
		
		view.getFullTextSearchBox().setText("");
		view.getMainClassificationSuggestBox().setSelected(null);
		view.getClassificationTopicSuggestBox().setSelected(null);
		view.getTopicSuggestBox().setSelected(null);
		
		view.setDefaultMessageOfSuggestionbox();
		
		initAllSkillSuggestions();
		
		initSkillFlexTable();
		
		view.setIsAsc(0);
		initSkillTableData(view.getStudent(),view.getIsAsc());
		
		view.getMainClassificationSuggestBox().setEnabled(true);
		view.getClassificationTopicSuggestBox().setEnabled(false);
		view.getTopicSuggestBox().setEnabled(false);
		
		ShowCriteria.mProxy=view.getMainClassificationSuggestBox().getSelected();
		ShowCriteria.tProxy=view.getTopicSuggestBox().getSelected();
		ShowCriteria.ctProxy=view.getClassificationTopicSuggestBox().getSelected();
		ShowCriteria.chkAsc=new Integer(view.getIsAsc()).toString();
		ShowCriteria.fullTextSearch=view.getFullTextSearchBox().getValue();
	}

	@Override
	public void showButtonClicked() {
		
		view.getHpErrorMessage().setVisible(false);
	
		this.mainClassificationId=view.getMainClassificationSuggestBox().getSelected() !=null ? view.getMainClassificationSuggestBox().getSelected().getId() : null;
		this.classificaitonTopicId=view.getClassificationTopicSuggestBox().getSelected()!=null ? view.getClassificationTopicSuggestBox().getSelected().getId() : null;
		this.topicId=view.getTopicSuggestBox().getSelected() !=null ? view.getTopicSuggestBox().getSelected().getId():null;
		
		if(mainClassificationId==null && classificaitonTopicId==null && topicId==null && view.getFullTextSearchBox().getValue()==""){			
			view.getHpErrorMessage().setVisible(true);
			view.getLblErrorMessage().setInnerHTML(constants.ErrorMessage());
		}
		else{
			
		view.setIsAsc(0);
		
		view.getPager().setStart(1);
		//view.getPager().setLength(20);*/
		
		initSkillTableData(view.getStudent(),view.getIsAsc());
		}
		
		ShowCriteria.mProxy=view.getMainClassificationSuggestBox().getSelected();
		ShowCriteria.tProxy=view.getTopicSuggestBox().getSelected();
		ShowCriteria.ctProxy=view.getClassificationTopicSuggestBox().getSelected();
		ShowCriteria.chkAsc=new Integer(view.getIsAsc()).toString();
		ShowCriteria.fullTextSearch=view.getFullTextSearchBox().getValue();
		
	}

	@Override
	public void generatePdfClicked() {
		String url = GWT.getHostPageBaseURL() + "downloadFile";
		Log.info("URL :" + url);
		Window.open(url, "", "");
		
	}

	@Override
	public void printPdfClicked() {
		
		showApplicationLoading(true);
		MainClassificationProxy mProxy=ShowCriteria.mProxy;
		ClassificationTopicProxy ctProxy=ShowCriteria.ctProxy;
		TopicProxy tProxy=ShowCriteria.tProxy;
		String fullTextSearch=ShowCriteria.fullTextSearch;
		
		Long mainClassifcationId=null;
		Long classifcationTopicId=null;
		Long topicId=null;
		
		if(mProxy!=null)
		{
			mainClassifcationId=mProxy.getId();
		}
		if(ctProxy!=null)
		{
			classifcationTopicId=ctProxy.getId();
		}
		if(tProxy!=null)
		{
			topicId=tProxy.getId();
		}
		
		Log.info("MainClassification :" + mainClassifcationId);
		Log.info("Classification Topic Id :" + classifcationTopicId);
		Log.info("Topic Id :" + topicId);
		requests.skillRequestNonRoo().retrieveHtmlFile(view.getStudent().getId(),mainClassifcationId,classifcationTopicId,topicId,fullTextSearch,new Integer(ShowCriteria.chkAsc).intValue()).fire(new Receiver<String>() {

			@Override
			public void onSuccess(String response) {
				
				Print.it(response);
				showApplicationLoading(false);
			}
			
		});
	
	}

	public void refreshFlextable(FlexTable table,int start,int length)
	{
		//view.setIsAsc(0);
		onRangeChanged(view.getStudent(),view.getIsAsc());
		//initSkillTableData(view.getStudent(),view.getIsAsc());
	}

	@Override
	public void chekBoxSelected(final SkillProxy skillProxy,final boolean isLevel1,final SkillLevelCheckboxViewImpl skillLevelCheckboxViewImpl) {
		
		view.getHpErrorMessage().setVisible(false);
		final int row =skillLevelCheckboxViewImpl.getRow();
		Boolean isDeleteOperation=true;
		final SkillLevelCheckboxViewImpl s =(SkillLevelCheckboxViewImpl)view.getSkillFlexTable().getWidget(row, 2);
		final SkillLevelCheckboxViewImpl s1 =(SkillLevelCheckboxViewImpl)view.getSkillFlexTable().getWidget(row, 3);
		
		Boolean isFirstSelected=s.getCheckbox().getValue();
		Boolean isSecondSelected=s1.getCheckbox().getValue();
		if(isFirstSelected || isSecondSelected){
			isDeleteOperation=false;
		}
		final Integer skillLevel;
		if(skillProxy.getSkillLevel()==null)
		skillLevel=0;
		else
		skillLevel=skillProxy.getSkillLevel().getLevelNumber();
		
		requests.skillAcquiredRequestNonRoo().acquireORDeleteSkill(view.getStudent().getId(),skillLevelCheckboxViewImpl.getSkillProxy().getId(),isLevel1,isDeleteOperation).fire(new Receiver<String>() {

			@Override
			public void onSuccess(String response) {
				//Window.alert("Operation complete");
				Log.info("Operation is :" + response);
				if(response.equalsIgnoreCase("INSERT")){
					skillLevelCheckboxViewImpl.getCheckbox().setValue(true);
					
					if(isLevel1 && skillLevel==2)
					{
						
						//decrement topic aquired skill
						if(!view.getSkillFlexTable().getRowFormatter().getStyleName(row).equalsIgnoreCase("redBG"))
						{
							if(skillLevelCheckboxViewImpl.getTopicRow() != 0)
							//changeProgress( skillLevelCheckboxViewImpl.getTopicRow(), false);
						 findProgressOfTopic(skillProxy.getTopic(),skillLevelCheckboxViewImpl.getTopicRow(),1,view.getStudent());
							
							//decrement classification topic aquired skill
							if(skillLevelCheckboxViewImpl.getClassificationTopicRow() != 0)
							//changeProgress( skillLevelCheckboxViewImpl.getClassificationTopicRow(), false);
							findProgressOfClassificationTopic(skillProxy.getTopic().getClassificationTopic(), skillLevelCheckboxViewImpl.getClassificationTopicRow(), 1,view.getStudent());
							
							//decrement main classification  aquired skill
							if(skillLevelCheckboxViewImpl.getMainClassificationRow() !=0)
							//changeProgress( skillLevelCheckboxViewImpl.getMainClassificationRow(), false);
							findProgressOfMainClassification(skillProxy.getTopic().getClassificationTopic().getMainClassification(),skillLevelCheckboxViewImpl.getMainClassificationRow(), 1,view.getStudent());	
						}
						
						view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "redBG");
						view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "greenBG");
						view.getSkillFlexTable().getRowFormatter().addStyleName(row, "yellowBG");
						
					}
					else
					{
						view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "redBG");
						view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "yellowBG");
						view.getSkillFlexTable().getRowFormatter().addStyleName(row, "greenBG");
						
						//increment topic aquired skill
						
						if(skillLevelCheckboxViewImpl.getTopicRow() != 0)
						findProgressOfTopic(skillProxy.getTopic(),skillLevelCheckboxViewImpl.getTopicRow(),1,view.getStudent());
						//changeProgress( skillLevelCheckboxViewImpl.getTopicRow(), true);						
						//increment classification topic aquired skill
						
						if(skillLevelCheckboxViewImpl.getClassificationTopicRow() != 0)
							findProgressOfClassificationTopic(skillProxy.getTopic().getClassificationTopic(), skillLevelCheckboxViewImpl.getClassificationTopicRow(), 1,view.getStudent());
						//changeProgress( skillLevelCheckboxViewImpl.getClassificationTopicRow(), true);
						
						//increment main classification  aquired skill
						if(skillLevelCheckboxViewImpl.getMainClassificationRow() !=0)
						//changeProgress( skillLevelCheckboxViewImpl.getMainClassificationRow(), true);
						findProgressOfMainClassification(skillProxy.getTopic().getClassificationTopic().getMainClassification(),skillLevelCheckboxViewImpl.getMainClassificationRow(), 1,view.getStudent());
					}
				}
				else if(response.equalsIgnoreCase("UPDATE")){
						if(isLevel1){
							s.getCheckbox().setValue(true);
							s1.getCheckbox().setValue(false);
							
						}
						else{
							s.getCheckbox().setValue(false);
							s1.getCheckbox().setValue(true);
						}
						
						
						if(isLevel1 && skillLevel==2)
						{
							//decrement topic aquired skill
							if(!view.getSkillFlexTable().getRowFormatter().getStyleName(row).equalsIgnoreCase("redBG"))
							{
								if(skillLevelCheckboxViewImpl.getTopicRow() != 0)
								findProgressOfTopic(skillProxy.getTopic(),skillLevelCheckboxViewImpl.getTopicRow(),1,view.getStudent());
								//changeProgress( skillLevelCheckboxViewImpl.getTopicRow(), false);
								
								//decrement classification topic aquired skill
								if(skillLevelCheckboxViewImpl.getClassificationTopicRow() !=0)
								findProgressOfClassificationTopic(skillProxy.getTopic().getClassificationTopic(), skillLevelCheckboxViewImpl.getClassificationTopicRow(), 1,view.getStudent());
								//changeProgress( skillLevelCheckboxViewImpl.getClassificationTopicRow(), false);
								
								//decrement main classification  aquired skill
								if(skillLevelCheckboxViewImpl.getMainClassificationRow() !=0)
								findProgressOfMainClassification(skillProxy.getTopic().getClassificationTopic().getMainClassification(),skillLevelCheckboxViewImpl.getMainClassificationRow(), 1,view.getStudent());
								//changeProgress( skillLevelCheckboxViewImpl.getMainClassificationRow(), false);
							}
							
							
							view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "redBG");
							view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "greenBG");
							view.getSkillFlexTable().getRowFormatter().addStyleName(row, "yellowBG");
							
							
						}
						else
						{
							view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "redBG");
							view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "yellowBG");
							view.getSkillFlexTable().getRowFormatter().addStyleName(row, "greenBG");
							
							//increment topic aquired skill
							if(skillLevelCheckboxViewImpl.getTopicRow() != 0)
								findProgressOfTopic(skillProxy.getTopic(),skillLevelCheckboxViewImpl.getTopicRow(),1,view.getStudent());
							//changeProgress( skillLevelCheckboxViewImpl.getTopicRow(), true);
								
							
							//increment classification topic aquired skill
							if(skillLevelCheckboxViewImpl.getClassificationTopicRow() != 0)
							findProgressOfClassificationTopic(skillProxy.getTopic().getClassificationTopic(), skillLevelCheckboxViewImpl.getClassificationTopicRow(), 1,view.getStudent());
							//changeProgress( skillLevelCheckboxViewImpl.getClassificationTopicRow(), true);
							
							//increment main classification  aquired skill
							if(skillLevelCheckboxViewImpl.getMainClassificationRow() !=0)
							findProgressOfMainClassification(skillProxy.getTopic().getClassificationTopic().getMainClassification(),skillLevelCheckboxViewImpl.getMainClassificationRow(), 1,view.getStudent());
							//changeProgress( skillLevelCheckboxViewImpl.getMainClassificationRow(), true);
						}
				}
				else if(response.equalsIgnoreCase("DELETE")){
					s.getCheckbox().setValue(false);
					s1.getCheckbox().setValue(false);
					
					Log.info("Style name :"+ view.getSkillFlexTable().getRowFormatter().getStyleName(row));
					
					//decrement topic aquired skill
					if(!view.getSkillFlexTable().getRowFormatter().getStyleName(row).equalsIgnoreCase("yellowBG"))
					{
						if(skillLevelCheckboxViewImpl.getTopicRow() !=0)
						//changeProgress( skillLevelCheckboxViewImpl.getTopicRow(), false);
						findProgressOfTopic(skillProxy.getTopic(),skillLevelCheckboxViewImpl.getTopicRow(),1,view.getStudent());
						
						//decrement classification topic aquired skill
						if(skillLevelCheckboxViewImpl.getClassificationTopicRow() !=0)
						//changeProgress( skillLevelCheckboxViewImpl.getClassificationTopicRow(), false);
						findProgressOfClassificationTopic(skillProxy.getTopic().getClassificationTopic(), skillLevelCheckboxViewImpl.getClassificationTopicRow(), 1,view.getStudent());
						
						//decrement main classification  aquired skill
						if(skillLevelCheckboxViewImpl.getMainClassificationRow() !=0)
						//changeProgress( skillLevelCheckboxViewImpl.getMainClassificationRow(), false);
						findProgressOfMainClassification(skillProxy.getTopic().getClassificationTopic().getMainClassification(),skillLevelCheckboxViewImpl.getMainClassificationRow(), 1,view.getStudent());	
					}
					
					view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "greenBG");
					view.getSkillFlexTable().getRowFormatter().removeStyleName(row, "yellowBG");
					view.getSkillFlexTable().getRowFormatter().addStyleName(row, "redBG");
					
					
				}
				else if(response.equals("ERROR")){					
					view.getHpErrorMessage().setVisible(true);
					view.getLblErrorMessage().setInnerHTML(constants.skillAcquireError());
					
				}
				
				
				
			}
		});
		
	}
	
	public void changeProgress(int row,boolean incr)
	{
		String topicProgress=((Label)((HorizontalPanel)view.getSkillFlexTable().getWidget(row, 0)).getWidget(1)).getText();
		String tP[]=topicProgress.split("/");
		Long topicProgressValue=new Long(tP[0]);
		if(!incr)
		{
		--topicProgressValue;
		}
		else
		{
			++topicProgressValue;
		}
		String totalTopicSkill=tP[1];
		((Label)((HorizontalPanel)view.getSkillFlexTable().getWidget(row, 0)).getWidget(1)).setText(topicProgressValue.toString()+"/"+totalTopicSkill);
		((CustomProgressbar)view.getSkillFlexTable().getWidget(row, 1)).setProgress(topicProgressValue);
	}
	
	@Override
	public void findProgressOfMainClassification(
			MainClassificationProxy mProxy, final int row,final  int i, StudentProxy student) {
		
		requests.skillRequestNonRoo().findProgressOfMainClassification(mProxy, student.getId()).fire(new Receiver<String>() {

			@Override
			public void onSuccess(String response) {
				String mP[]=response.split("/");
				
				view.getSkillFlexTable().setWidget(row, i, view.createProgressBar(new Integer(mP[1]),new Integer(mP[0])));
				((Label)((HorizontalPanel)view.getSkillFlexTable().getWidget(row, 0)).getWidget(1)).setText(response);
				
			}
		});
		
	}

	@Override
	public void findProgressOfClassificationTopic(
			ClassificationTopicProxy ctProxy,final int row,final int i, StudentProxy student) {
		requests.skillRequestNonRoo().findProgressOfClassificationTopic(ctProxy, student.getId()).fire(new Receiver<String>() {

			@Override
			public void onSuccess(String response) {
				String mP[]=response.split("/");
				
				view.getSkillFlexTable().setWidget(row, i, view.createProgressBar(new Integer(mP[1]),new Integer(mP[0])));
				((Label)((HorizontalPanel)view.getSkillFlexTable().getWidget(row, 0)).getWidget(1)).setText(response);
				
			}
		});
		
		
	}

	@Override
	public void findProgressOfTopic(TopicProxy tproxy,final int row,final int i,
			StudentProxy student) {
		requests.skillRequestNonRoo().findProgressOfTopic(tproxy, student.getId()).fire(new Receiver<String>() {

			@Override
			public void onSuccess(String response) {
				String mP[]=response.split("/");
				
				view.getSkillFlexTable().setWidget(row, i, view.createProgressBar(new Integer(mP[1]),new Integer(mP[0])));
				((Label)((HorizontalPanel)view.getSkillFlexTable().getWidget(row, 0)).getWidget(1)).setText(response);
				
			}
		});
		
	}

	@Override
	public void shortCutClicked() {
		view.getPager().setStart(1);
		initSkillTableData(view.getStudent(),view.getIsAsc());
		//onRangeChanged(view.getStudent(),view.getIsAsc());
		
	}

	@Override
	public void exportPDF() {
		Log.info("exportPDF");
		
		MainClassificationProxy mProxy=ShowCriteria.mProxy;
		ClassificationTopicProxy ctProxy=ShowCriteria.ctProxy;
		TopicProxy tProxy=ShowCriteria.tProxy;
		String fullTextSearch=ShowCriteria.fullTextSearch;
		
		String mainClassifcationId="0";
		String classifcationTopicId="0";
		String topicId="0";
		
		mProxy=null;
		ctProxy=null;
		tProxy=null;
		fullTextSearch="";
		if(mProxy!=null)
		{
			mainClassifcationId=mProxy.getId().toString();
		}
		if(ctProxy!=null)
		{
			classifcationTopicId=ctProxy.getId().toString();
		}
		if(tProxy!=null)
		{
			topicId=tProxy.getId().toString();
		}
		
		String chkAsc=ShowCriteria.chkAsc;
		String url=GWT.getHostPageBaseURL()+"SkillPdfExport?studentId="+view.getStudent().getId()+"&mainClassifcationId="+mainClassifcationId+"&classifcationId="+classifcationTopicId+"&topicId="+topicId+"&chkAsc="+chkAsc+"&fullTextSearch="+fullTextSearch;
		Log.info("url :" + url);
		Window.open(url, "skill"+view.getStudent().getName()+".pdf", "enabled");
		
	}
	public void showApplicationLoading(Boolean show) {
		requests.getEventBus().fireEvent(new ApplicationLoadingScreenEvent(show));

	}
	/*@Override
	public Boolean isSkillAcquiredbyStudentAtFirstLevel(Long studentID,
			Long skillId, Long skillLevelID) {
		
		
		requests.skillRequestNonRoo().isSkillAcquiredbyStudentAtFirstLevel(studentID, skillId, skillLevelID).fire(new Receiver<Boolean>() {

			@Override
			public void onSuccess(Boolean response) {
				result=response;
				return result;
			}
		});
		
	}*/

	@Override
	public void iconButtonClicked(final SkillProxy skillProxy,final SkillLevelIconButtonViewImpl skillLevelIconButtonViewImpl) {
		Log.info("Skill Proxy is :" + skillProxy.getId());
		
		view.getHpErrorMessage().setVisible(false);
		
		if(skillLevelIconButtonViewImpl.isSave()==false)
		{
			
			final String comment=((SkillLevelTextAreaViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().getText();
			
			requests.skillRequestNonRoo().addCommnets(skillProxy.getId(),view.getStudent().getId(), comment).fire(new Receiver<String>() {

				@Override
				public void onSuccess(String response) {
					Log.info("Operation Was :" + response);
					if(response.compareToIgnoreCase("FAILURE")==0)
					{
						/*view.getHpErrorMessage().setVisible(true);
						view.getLblErrorMessage().setInnerHTML(constants.commentError());
						((SkillLevelTextAreaViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setEnabled(true);
						((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setStyleName("skillTextAreaEnabled");
						((SkillLevelIconButtonViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),5)).getIconButton().setClassName("ui-icon ui-icon-disk");
						((SkillLevelIconButtonViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),5)).setSave(true);*/
						
						view.getHpErrorMessage().setVisible(true);
						view.getLblErrorMessage().setInnerHTML(constants.commentError());
						((SkillLevelTextAreaViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setEnabled(false);
						((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().removeStyleName("skillTextAreaEnabled");
						((SkillLevelIconButtonViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),5)).getIconButton().setClassName("ui-icon ui-icon-pencil");
						((SkillLevelIconButtonViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),5)).setSave(false);
						
						requests.skillRequestNonRoo().getCommentOfStudentForSkill(skillProxy.getId(),view.getStudent().getId()).fire(new Receiver<String>() {
							@Override
							public void onSuccess(String response) 
							{
								//if(response.getSkillComment() !=null && response.getSkillComment().getComment() !=null)
								//{
									((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setText(UtilityLogBook.getFormatedString(response,40));
									((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setTitle(response);
								//}
								/*else
								((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setText("");*/
									
							}
						
						});
						
					}
					else
					{
						((SkillLevelTextAreaViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setEnabled(false);
						((SkillLevelIconButtonViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),5)).getIconButton().setClassName("ui-icon ui-icon-pencil");
						((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().removeStyleName("skillTextAreaEnabled");
						((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setStyleName("skillTextArea");
						((SkillLevelIconButtonViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),5)).setSave(false);
						
						/*requests.skillRequest().findSkill(skillProxy.getId()).with("skillComment").fire(new Receiver<SkillProxy>() {

							@Override
							public void onSuccess(SkillProxy response) 
							{
								((SkillLevelIconButtonViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),5)).setSkillProxy(response);	
								((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setText(UtilityLogBook.getFormatedString(comment,45));
								((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setTitle(comment);
							}
						});
						*/
							/*requests.skillRequestNonRoo().getCommentOfStudentForSkill(skillProxy.getId(),view.getStudent().getId()).fire(new Receiver<String>() {
							@Override
							public void onSuccess(String response) 
							{
									((SkillLevelIconButtonViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),5)).setSkillcomment(response);
									((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setText(UtilityLogBook.getFormatedString(response,45));
									((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setTitle(response);
								
							}
						
						});*/
						((SkillLevelIconButtonViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),5)).setSkillcomment(comment);
						((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setText(UtilityLogBook.getFormatedString(comment,40));
						((SkillLevelTextAreaViewImpl)skillFlexTable.getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setTitle(comment);
			
					}
				}
			});
		}
		else{
		
			((SkillLevelTextAreaViewImpl)view.getSkillFlexTable().getWidget(skillLevelIconButtonViewImpl.getRow(),4)).getTextArea().setEnabled(true);
		}
		
		
	}

	
}
