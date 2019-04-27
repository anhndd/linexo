package vn.edu.hcmut.linexo.presentation.view.room;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.databinding.ActivityRoomBinding;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.presentation.view.BaseActivity;
import vn.edu.hcmut.linexo.presentation.view.play.PlayActivity;
import vn.edu.hcmut.linexo.presentation.view.splash.SplashActivity;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.Tool;

public class RoomActivity extends BaseActivity {

    @Inject
    @Named("RoomViewModel")
    public ViewModel viewModel;

    ActivityRoomBinding binding;
    Disposable disposable;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG_GG = "GoogleActivity";
    private static final String TAG_FB = "FacebookLogin";

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        binding.lstRoom.setLayoutManager(new GridLayoutManager(this, 2));
        binding.lstRoom.setHasFixedSize(true);

        addControlKeyboardView(binding.edtSearch);
    }

    @Override
    public void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room);
        viewModel = (ViewModel) getLastCustomNonConfigurationInstance();
        if (viewModel == null) {
            SplashActivity.getAppComponent(this).inject(this);
        }
        binding.setViewModel((RoomViewModel) viewModel);
    }

    @Override
    public void onSubscribeViewModel() {
        viewModel.subscribeObserver(new Observer<Event>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Event event) {
                switch (event.getType()) {
                    case Event.SHOW_LOGIN: {
                        Dialog loginDialog = new Dialog(RoomActivity.this);
                        loginDialog.setContentView(R.layout.popup_login);

                        Button btnGoogle = loginDialog.findViewById(R.id.btn_google);
                        btnGoogle.setOnClickListener(view -> {
                            loginGoogle();
                            loginDialog.dismiss();
                        });

                        Button btnFacebookFace = loginDialog.findViewById(R.id.btn_facebook);
                        LoginButton btnFacebook = loginDialog.findViewById(R.id.btn_facebook_invisibility);

                        btnFacebookFace.setOnClickListener(v -> btnFacebook.performClick());

                        btnFacebook.setReadPermissions("email", "public_profile");
                        btnFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.e(TAG_FB, "facebook:onSuccess:" + loginResult);
                                handleFacebookAccessToken(loginResult.getAccessToken());
                                loginDialog.dismiss();
                            }

                            @Override
                            public void onCancel() {
                                Log.e(TAG_FB, "facebook:onCancel");
                            }

                            @Override
                            public void onError(FacebookException error) {
                                Log.e(TAG_FB, "facebook:onError" + error.getMessage(), error);
                            }
                        });

                        loginDialog.show();
                        break;
                    }
                    case Event.SHOW_PLAY_ACTIVITY: {
                        int idRoom = (int) event.getData()[0];
                        Intent intent = new Intent(RoomActivity.this, PlayActivity.class);
                        intent.putExtra("idRoom",idRoom);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case Event.SHOW_POPUP_USER_ON: {
                        Context wrapper = new ContextThemeWrapper(RoomActivity.this, R.style.AppTheme_PopupMenu);
                        //Creating the instance of PopupMenu
                        PopupMenu popup = new PopupMenu(wrapper, binding.avatar);
                        Tool.forcePopupMenuShowIcon(popup);
                        //Inflating the Popup using xml file

                        popup.getMenuInflater().inflate(R.menu.popup_logout, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.it_rating:
                                        break;
                                    case R.id.it_logout:
                                        mGoogleSignInClient.signOut();
                                        LoginManager.getInstance().logOut();
                                        ((ViewModelCallback) viewModel).onHelp(Event.create(Event.LOGOUT));
                                        break;
                                }
                                return true;
                            }
                        });
                        popup.show(); //showing popup menu
//
//                        ((ViewModelCallback) viewModel).onHelp(Event.create(Event.CLICK_AVATAR_POPUP_MENU, popup));
                        break;
                    }
                    case Event.SHOW_POPUP_USER_OFF: {
                        Context wrapper = new ContextThemeWrapper(RoomActivity.this, R.style.AppTheme_PopupMenu);
                        //Creating the instance of PopupMenu
                        PopupMenu popup = new PopupMenu(wrapper, binding.avatar);
                        Tool.forcePopupMenuShowIcon(popup);
                        //Inflating the Popup using xml file

                        popup.getMenuInflater().inflate(R.menu.popup_login, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.it_rating:
                                        break;
                                }
                                return true;
                            }
                        });
                        popup.show(); //showing popup menu
//
//                        ((ViewModelCallback) viewModel).onHelp(Event.create(Event.CLICK_AVATAR_POPUP_MENU, popup));
                        break;
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onUnSubscribeViewModel() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public Object onSaveViewModel() {
        return null;
    }

    @Override
    public void onEndTaskViewModel() {

    }

    private void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG_GG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG_GG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG_GG, "signInWithCredential:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            User user = new User(firebaseUser.getUid(), firebaseUser.getEmail(),
                                    firebaseUser.getPhotoUrl().toString(), firebaseUser.getDisplayName(), 0, System.currentTimeMillis());
                            ((RoomViewModel) viewModel).onHelp(Event.create(Event.LOGIN_USER, user));
//                            Toast.makeText(RoomActivity.this, user.getDisplayName(), Toast.LENGTH_LONG).show();

                        } else {
                            Log.w(TAG_GG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.e(TAG_FB, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println(task.isSuccessful());
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG_FB, "signInWithCredential:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            User user = new User(firebaseUser.getUid(), firebaseUser.getEmail(),
                                    firebaseUser.getPhotoUrl().toString(), firebaseUser.getDisplayName(), 0, System.currentTimeMillis());
                            ((RoomViewModel) viewModel).onHelp(Event.create(Event.LOGIN_USER, user));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG_FB, "signInWithCredential:failure" + task.getException().getMessage(), task.getException());
                            Toast.makeText(RoomActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}