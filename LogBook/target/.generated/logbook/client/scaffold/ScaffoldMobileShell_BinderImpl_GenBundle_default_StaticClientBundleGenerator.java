package logbook.client.scaffold;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator implements logbook.client.scaffold.ScaffoldMobileShell_BinderImpl_GenBundle {
  private static ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator _instance0 = new ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator();
  private void backButtonImageInitializer() {
    backButtonImage = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "backButtonImage",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(bundledImage_None),
      0, 0, 9, 27, false, false
    );
  }
  private static class backButtonImageInitializer {
    static {
      _instance0.backButtonImageInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return backButtonImage;
    }
  }
  public com.google.gwt.resources.client.ImageResource backButtonImage() {
    return backButtonImageInitializer.get();
  }
  private void titleGradientInitializer() {
    titleGradient = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
      "titleGradient",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(bundledImage_Horizontal),
      0, 0, 43, 44, false, false
    );
  }
  private static class titleGradientInitializer {
    static {
      _instance0.titleGradientInitializer();
    }
    static com.google.gwt.resources.client.ImageResource get() {
      return titleGradient;
    }
  }
  public com.google.gwt.resources.client.ImageResource titleGradient() {
    return titleGradientInitializer.get();
  }
  private void styleInitializer() {
    style = new logbook.client.scaffold.ScaffoldMobileShell_BinderImpl_GenCss_style() {
      private boolean injected;
      public boolean ensureInjected() {
        if (!injected) {
          injected = true;
          com.google.gwt.dom.client.StyleInjector.inject(getText());
          return true;
        }
        return false;
      }
      public String getName() {
        return "style";
      }
      public String getText() {
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GAK-4ALBJK{height:" + ((ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.titleGradient()).getHeight() + "px")  + ";overflow:" + ("hidden")  + ";background:" + ("url(\"" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.titleGradient()).getSafeUri().asString() + "\") -" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.titleGradient()).getLeft() + "px -" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.titleGradient()).getTop() + "px  repeat-x")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#a0abbf")  + ";height:" + ("44px")  + ";}.GAK-4ALBKK{height:" + ("100%")  + ";width:" + ("100%")  + ";}.GAK-4ALBIK{color:" + ("#444")  + ";font-size:" + ("12pt")  + ";font-weight:" + ("bold")  + ";text-shadow:") + (("#ddd"+ " " +"1px"+ " " +"1px"+ " " +"1px")  + ";}.GAK-4ALBDK{width:" + ("50px")  + ";padding-right:" + ("5px")  + ";}.GAK-4ALBGK{width:" + ("50px")  + ";padding-left:" + ("5px")  + ";}.GAK-4ALBFK{color:" + ("#4d657f")  + ";font-size:" + ("9pt")  + ";font-weight:" + ("bold")  + ";border:" + ("1px"+ " " +"solid"+ " " +"#aebbdd")  + ";padding:" + ("4px"+ " " +"6px")  + ";background:" + ("#ecf1fd") ) + (";height:" + ("27px")  + ";}.GAK-4ALBCK{border-right:" + ("none")  + ";padding-right:" + ("2px")  + ";margin-right:" + ("0")  + ";}.GAK-4ALBEK{height:" + ((ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getHeight() + "px")  + ";width:" + ((ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getWidth() + "px")  + ";overflow:" + ("hidden")  + ";background:" + ("url(\"" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getSafeUri().asString() + "\") -" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getLeft() + "px -" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getTop() + "px  no-repeat")  + ";}.GAK-4ALBHK{height:" + ("32px")  + ";color:" + ("#222")  + ";text-align:") + (("center")  + ";background:" + ("white")  + ";padding:" + ("2px"+ " " +"0")  + ";border-top:" + ("1px"+ " " +"solid"+ " " +"#a0abbf")  + ";}")) : ((".GAK-4ALBJK{height:" + ((ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.titleGradient()).getHeight() + "px")  + ";overflow:" + ("hidden")  + ";background:" + ("url(\"" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.titleGradient()).getSafeUri().asString() + "\") -" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.titleGradient()).getLeft() + "px -" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.titleGradient()).getTop() + "px  repeat-x")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#a0abbf")  + ";height:" + ("44px")  + ";}.GAK-4ALBKK{height:" + ("100%")  + ";width:" + ("100%")  + ";}.GAK-4ALBIK{color:" + ("#444")  + ";font-size:" + ("12pt")  + ";font-weight:" + ("bold")  + ";text-shadow:") + (("#ddd"+ " " +"1px"+ " " +"1px"+ " " +"1px")  + ";}.GAK-4ALBDK{width:" + ("50px")  + ";padding-left:" + ("5px")  + ";}.GAK-4ALBGK{width:" + ("50px")  + ";padding-right:" + ("5px")  + ";}.GAK-4ALBFK{color:" + ("#4d657f")  + ";font-size:" + ("9pt")  + ";font-weight:" + ("bold")  + ";border:" + ("1px"+ " " +"solid"+ " " +"#aebbdd")  + ";padding:" + ("4px"+ " " +"6px")  + ";background:" + ("#ecf1fd") ) + (";height:" + ("27px")  + ";}.GAK-4ALBCK{border-left:" + ("none")  + ";padding-left:" + ("2px")  + ";margin-left:" + ("0")  + ";}.GAK-4ALBEK{height:" + ((ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getHeight() + "px")  + ";width:" + ((ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getWidth() + "px")  + ";overflow:" + ("hidden")  + ";background:" + ("url(\"" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getSafeUri().asString() + "\") -" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getLeft() + "px -" + (ScaffoldMobileShell_BinderImpl_GenBundle_default_StaticClientBundleGenerator.this.backButtonImage()).getTop() + "px  no-repeat")  + ";}.GAK-4ALBHK{height:" + ("32px")  + ";color:" + ("#222")  + ";text-align:") + (("center")  + ";background:" + ("white")  + ";padding:" + ("2px"+ " " +"0")  + ";border-top:" + ("1px"+ " " +"solid"+ " " +"#a0abbf")  + ";}"));
      }
      public java.lang.String backButton(){
        return "GAK-4ALBCK";
      }
      public java.lang.String backButtonCell(){
        return "GAK-4ALBDK";
      }
      public java.lang.String backButtonImage(){
        return "GAK-4ALBEK";
      }
      public java.lang.String button(){
        return "GAK-4ALBFK";
      }
      public java.lang.String editButtonCell(){
        return "GAK-4ALBGK";
      }
      public java.lang.String login(){
        return "GAK-4ALBHK";
      }
      public java.lang.String title(){
        return "GAK-4ALBIK";
      }
      public java.lang.String titlebar(){
        return "GAK-4ALBJK";
      }
      public java.lang.String titlebarLayout(){
        return "GAK-4ALBKK";
      }
    }
    ;
  }
  private static class styleInitializer {
    static {
      _instance0.styleInitializer();
    }
    static logbook.client.scaffold.ScaffoldMobileShell_BinderImpl_GenCss_style get() {
      return style;
    }
  }
  public logbook.client.scaffold.ScaffoldMobileShell_BinderImpl_GenCss_style style() {
    return styleInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static final java.lang.String bundledImage_None = GWT.getModuleBaseForStaticFiles() + "86936A39E7B2A9FE81863C792627814D.cache.png";
  private static final java.lang.String bundledImage_Horizontal = GWT.getModuleBaseForStaticFiles() + "4DB30A786149AA814D5C3C8FF1B89153.cache.png";
  private static com.google.gwt.resources.client.ImageResource backButtonImage;
  private static com.google.gwt.resources.client.ImageResource titleGradient;
  private static logbook.client.scaffold.ScaffoldMobileShell_BinderImpl_GenCss_style style;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      backButtonImage(), 
      titleGradient(), 
      style(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("backButtonImage", backButtonImage());
        resourceMap.put("titleGradient", titleGradient());
        resourceMap.put("style", style());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'backButtonImage': return this.@logbook.client.scaffold.ScaffoldMobileShell_BinderImpl_GenBundle::backButtonImage()();
      case 'titleGradient': return this.@logbook.client.scaffold.ScaffoldMobileShell_BinderImpl_GenBundle::titleGradient()();
      case 'style': return this.@logbook.client.scaffold.ScaffoldMobileShell_BinderImpl_GenBundle::style()();
    }
    return null;
  }-*/;
}
