// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package logbook.client.managed.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.client.DateTimeFormatRenderer;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import java.util.HashSet;
import java.util.Set;
import logbook.client.managed.proxy.AdministratorProxy;
import logbook.client.scaffold.place.AbstractProxyListView;

public abstract class AdministratorListView_Roo_Gwt extends AbstractProxyListView<AdministratorProxy> {

    @UiField
    CellTable<AdministratorProxy> table;

    protected Set<String> paths = new HashSet<String>();

    public void init() {
        paths.add("id");
        table.addColumn(new TextColumn<AdministratorProxy>() {

            Renderer<java.lang.Long> renderer = new AbstractRenderer<java.lang.Long>() {

                public String render(java.lang.Long obj) {
                    return obj == null ? "" : String.valueOf(obj);
                }
            };

            @Override
            public String getValue(AdministratorProxy object) {
                return renderer.render(object.getId());
            }
        }, "Id");
        paths.add("email");
        table.addColumn(new TextColumn<AdministratorProxy>() {

            Renderer<java.lang.String> renderer = new AbstractRenderer<java.lang.String>() {

                public String render(java.lang.String obj) {
                    return obj == null ? "" : String.valueOf(obj);
                }
            };

            @Override
            public String getValue(AdministratorProxy object) {
                return renderer.render(object.getEmail());
            }
        }, "Email");
        paths.add("name");
        table.addColumn(new TextColumn<AdministratorProxy>() {

            Renderer<java.lang.String> renderer = new AbstractRenderer<java.lang.String>() {

                public String render(java.lang.String obj) {
                    return obj == null ? "" : String.valueOf(obj);
                }
            };

            @Override
            public String getValue(AdministratorProxy object) {
                return renderer.render(object.getName());
            }
        }, "Name");
        paths.add("preName");
        table.addColumn(new TextColumn<AdministratorProxy>() {

            Renderer<java.lang.String> renderer = new AbstractRenderer<java.lang.String>() {

                public String render(java.lang.String obj) {
                    return obj == null ? "" : String.valueOf(obj);
                }
            };

            @Override
            public String getValue(AdministratorProxy object) {
                return renderer.render(object.getPreName());
            }
        }, "Pre Name");
        paths.add("version");
        table.addColumn(new TextColumn<AdministratorProxy>() {

            Renderer<java.lang.Integer> renderer = new AbstractRenderer<java.lang.Integer>() {

                public String render(java.lang.Integer obj) {
                    return obj == null ? "" : String.valueOf(obj);
                }
            };

            @Override
            public String getValue(AdministratorProxy object) {
                return renderer.render(object.getVersion());
            }
        }, "Version");
    }
}