package com.example.dhruvkalia.newproject.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhruvkalia.newproject.Activity.MoneyReceivedActivity;
import com.example.dhruvkalia.newproject.R;

import static android.graphics.Color.GRAY;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{


    public static String CODE_MONEY_SENT_ID = "AMOUNT_SENT";
    public static String CODE_SENDER_NAME = "SENDER_NAME";

    private View view;
    private NfcAdapter nfcAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View vieww, @Nullable Bundle savedInstanceState) {

        view = vieww;

        //on click for send money button
        ((Button) view.findViewById(R.id.button_send_money)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkIfNFCIsEnabledOrNot() == 0){
                    // if nfc is switched off
                    openDialog();
                }
            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(getContext());

        nfcAdapter.setNdefPushMessageCallback(new NfcAdapter.CreateNdefMessageCallback() {
            @Override
            public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
                {
                    String message = ((EditText) view.findViewById(R.id.edit_text_amount)).getText().toString();
                    NdefRecord ndefRecord = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
                        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
                        return ndefMessage;

                    } else{
                        Toast.makeText(getContext(), R.string.nfc_not_present, Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }
        }, getActivity(),getActivity());

        if(checkIfNFCIsEnabledOrNot() == -1){
            //if nfc is not present
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setText(R.string.nfc_not_present);
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setTextColor(getResources().getColor(R.color.gray));
        } else if(checkIfNFCIsEnabledOrNot() == 0){
            // if nfc is switched off
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setText(R.string.nfc_off);
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setTextColor(getResources().getColor(R.color.gray));
        } else if(checkIfNFCIsEnabledOrNot() == 1){
            // if nfc is switched on
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setText(R.string.nfc_on);
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(R.string.nfc_in_settings)
                .setTitle(R.string.nfc_not_enabled);

        AlertDialog dialog = builder.create();

        builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do nothing
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.open_settings, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //open settings
                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        builder.show();
    }

    private int checkIfNFCIsEnabledOrNot(){
        //check if the device has NFC feature or not
        if(nfcAdapter == null){
            //if nfc is not present in the device
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setText(R.string.nfc_not_present);
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setTextColor(getResources().getColor(R.color.gray));
            return -1;
        }
        //check if NFC is enabled or not
        else if(nfcAdapter.isEnabled()) {
            //if yes, change the color of text to green, also change the text
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setText(R.string.nfc_on);
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setTextColor(getResources().getColor(R.color.colorPrimary));
            return 1;
        }
        else if(!nfcAdapter.isEnabled()){
            //if no, change the color of text to gray, also change the text
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setText(R.string.nfc_off);
            ((TextView) view.findViewById(R.id.text_view_check_nfc)).setTextColor(getResources().getColor(R.color.gray));
            return 0;
        }

        return 0;
    }

    @Override
    public void onResume(){
        super.onResume();
        checkIfNFCIsEnabledOrNot();
        // for recieving money
        Intent intent = getActivity().getIntent();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())){
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage ndefMessage = (NdefMessage) rawMessages[0];
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            //money received is in message
            Toast.makeText(getContext(),"Money received is " + message ,Toast.LENGTH_LONG).show();

            Intent intentMoney = new Intent(getActivity(), MoneyReceivedActivity.class);
            intentMoney.putExtra(CODE_MONEY_SENT_ID,message);
            startActivity(intentMoney);

        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
