// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package logbook.client.managed.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.LongBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import logbook.client.managed.activity.StudentEditActivityWrapper;
import logbook.client.managed.activity.StudentEditActivityWrapper.View;
import logbook.client.managed.proxy.SkillAcquiredProxy;
import logbook.client.managed.proxy.StudentProxy;
import logbook.client.scaffold.place.ProxyEditView;
import logbook.client.scaffold.ui.*;
import logbook.shared.Gender;
import logbook.shared.StudentStatus;
import logbook.shared.StudyYears;

public abstract class StudentEditView_Roo_Gwt extends Composite implements View<StudentEditView> {

    @UiField
    TextBox studentId;

    @UiField
    TextBox shib_id;

    @UiField
    TextBox email;

    @UiField(provided = true)
    ValueListBox<Gender> gender = new ValueListBox<Gender>(new AbstractRenderer<logbook.shared.Gender>() {

        public String render(logbook.shared.Gender obj) {
            return obj == null ? "" : String.valueOf(obj);
        }
    });

    @UiField
    TextBox name;

    @UiField
    TextBox preName;

    @UiField(provided = true)
    ValueListBox<StudentStatus> studentStatus = new ValueListBox<StudentStatus>(new AbstractRenderer<logbook.shared.StudentStatus>() {

        public String render(logbook.shared.StudentStatus obj) {
            return obj == null ? "" : String.valueOf(obj);
        }
    });

    @UiField(provided = true)
    ValueListBox<StudyYears> studyYear = new ValueListBox<StudyYears>(new AbstractRenderer<logbook.shared.StudyYears>() {

        public String render(logbook.shared.StudyYears obj) {
            return obj == null ? "" : String.valueOf(obj);
        }
    });

    @UiField
    SkillAcquiredSetEditor skillAcquired;

    public void setStudentStatusPickerValues(Collection<logbook.shared.StudentStatus> values) {
        studentStatus.setAcceptableValues(values);
    }

    public void setGenderPickerValues(Collection<logbook.shared.Gender> values) {
        gender.setAcceptableValues(values);
    }

    public void setSkillAcquiredPickerValues(Collection<logbook.client.managed.proxy.SkillAcquiredProxy> values) {
        skillAcquired.setAcceptableValues(values);
    }

    public void setStudyYearPickerValues(Collection<logbook.shared.StudyYears> values) {
        studyYear.setAcceptableValues(values);
    }
}