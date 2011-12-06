package org.jboss.as.console.client.widgets.pages;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Heiko Braun
 * @date 12/6/11
 */
public class PagedView {

    private DeckPanel deck;
    private LinkBar bar;
    private boolean navOnFirstPage = false;
    private Widget navigationBar;

    public PagedView(boolean navOnFirstPage) {
        this.navOnFirstPage = navOnFirstPage;

        deck = new DeckPanel();
        deck.addStyleName("fill-layout");
        bar = new LinkBar();
    }

    public PagedView() {
        this(false);
    }

    public Widget asWidget() {

        LayoutPanel layout = new LayoutPanel();
        layout.setStyleName("fill-layout");

        navigationBar = bar.asWidget();
        layout.add(navigationBar);
        layout.add(deck);

        layout.setWidgetTopHeight(navigationBar, 0, Style.Unit.PX, 20, Style.Unit.PX);
        layout.setWidgetTopHeight(deck, 20, Style.Unit.PX, 100, Style.Unit.PCT);

        navigationBar.setVisible(navOnFirstPage);

        return layout;
    }

    public void addPage(String title, Widget pageWidget)
    {
        deck.add(pageWidget);
        final int index = deck.getWidgetCount()-1;

        bar.addLink(title, new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                showPage(index);
            }
        });
    }

    public void showPage(int index) {

        if(!navOnFirstPage && navigationBar!=null)
        {
            // only on subsequent pages
            if(index>0)
                navigationBar.setVisible(true);
            else
                navigationBar.setVisible(false);

        }

        deck.showWidget(index);
        bar.setActive(index);
    }
}
