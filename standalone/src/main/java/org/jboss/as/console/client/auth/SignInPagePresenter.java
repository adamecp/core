package org.jboss.as.console.client.auth;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.*;
import org.jboss.as.console.client.BootstrapContext;
import org.jboss.as.console.client.NameTokens;


public class SignInPagePresenter extends
        Presenter<SignInPagePresenter.MyView, SignInPagePresenter.MyProxy> implements
        SignInPageUIHandlers {

    private final PlaceManager placeManager;
    private CurrentUser user;
    private BootstrapContext bootstrap;

    // private final ErrorDialogPresenterWidget errorDialog;

    @ProxyStandard
    @NameToken(NameTokens.signInPage)
    @NoGatekeeper
    public interface MyProxy extends Proxy<SignInPagePresenter>, Place {
    }

    public interface MyView extends View, HasUiHandlers<SignInPageUIHandlers> {
        HasClickHandlers getSignInButton();
        TextBox getUserName();
        TextBox getPassword();
        void resetAndFocus();
    }

    @Inject
    public SignInPagePresenter(EventBus eventBus, MyView view, MyProxy proxy,
                               PlaceManager placeManager, CurrentUser user,
                               BootstrapContext bootstrap) {
        super(eventBus, view, proxy);

        this.placeManager = placeManager;
        this.user = user;
        this.bootstrap = bootstrap;
    }

    @Override
    protected void onBind() {
        super.onBind();
        registerHandler(getView().getSignInButton().addClickHandler(
                new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        doLogin();
                    }
                }));

        final KeyDownHandler handler = new KeyDownHandler()
        {
            public void onKeyDown(KeyDownEvent event)
            {
                if (KeyCodes.KEY_ENTER == event.getNativeKeyCode())
                    doLogin();
            }
        };

        registerHandler(getView().getPassword().addKeyDownHandler(handler));
        registerHandler(getView().getUserName().addKeyDownHandler(handler));

    }

    @Override
    protected void onReset() {
        super.onReset();
        getView().resetAndFocus();
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    void doLogin() {

        String userName = getView().getUserName().getText();
        String password = getView().getPassword().getText();

        if (isValidUserName(userName) && (isValidPassword(password))) {

            // TODO: actual login needs to be implemented

            Log.info("Log in as: " + userName);
            user.setUserName(userName);
            user.setLoggedIn(true);

            /*if(bootstrap.hasProperty(BootstrapContext.INITIAL_TOKEN))
            {
                History.newItem(bootstrap.getProperty(BootstrapContext.INITIAL_TOKEN));
            }
            else
            {
                PlaceRequest myRequest = new PlaceRequest(NameTokens.mainLayout);
                placeManager.revealPlace(myRequest);
            }                                       */

            PlaceRequest myRequest = new PlaceRequest(NameTokens.mainLayout);
            placeManager.revealPlace(myRequest);

            // notify listeners
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    getEventBus().fireEvent(new AuthenticationEvent(user));
                }
            });
        }
        else {
            showErrorDialog();
        }
    }

    private static boolean isValidUserName(String username)
    {
        return true;
    }

    private static boolean isValidPassword(String password)
    {
        return true;
    }

    @Override
    public void showErrorDialog() {
        //addToPopupSlot(errorDialog);
        // TODO: implement authentication error handling
        Log.error("Login failed!");

    }
}
