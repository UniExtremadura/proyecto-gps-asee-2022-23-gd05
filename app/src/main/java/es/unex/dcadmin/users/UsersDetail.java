package es.unex.dcadmin.users;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import es.unex.dcadmin.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";
    public static final String ARG_PARAM3 = "param3";
    public static final String ARG_PARAM4 = "param4";

    private String mParam1;
    private String mParam2;
    private Long mParam3;
    private String mParam4;

    private TextView user_name;
    private TextView server_name;
    private TextView user_id;
    private ImageView user_avatar;

    private Member member;

    public UsersDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersDetail newInstance(String param1, String param2, String param3, String param4) {
        UsersDetail fragment = new UsersDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if(bundle != null){
            try {
                member = new Member(bundle.getLong(ARG_PARAM3),
                                    bundle.getString(ARG_PARAM1),
                                    new URL(bundle.getString(ARG_PARAM4)),
                                    bundle.getString(ARG_PARAM2));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getLong(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.users_detail, container, false);

        user_name = v.findViewById(R.id.user_name);
        server_name = v.findViewById(R.id.server_name);
        user_id = v.findViewById(R.id.user_id);
        user_avatar = v.findViewById(R.id.user_avatar);

        View screen = v.findViewById(R.id.userDetailScreen);
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        user_name.setText(member.getName());
        server_name.setText(member.getServer());
        user_id.setText((String.valueOf(member.getId())));

        try {
            Picasso.get().load(String.valueOf(member.getAvatar().toURI())).into(user_avatar);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        return v;
    }
}