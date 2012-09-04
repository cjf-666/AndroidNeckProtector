package org.footoo.cjflsq.neck.cervicaltest;

import org.footoo.cjflsq.neck.MyApplication;
import org.footoo.cjflsq.neck.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IssueFragment extends Fragment {
    
    private int index;
    
    private static String[] issueString = MyApplication.getAppContext().getResources().getStringArray(R.array.test_issues);
    
    public IssueFragment(int num) {
	super();
	index = num;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	// Inflate the layout for this fragment
	View issueView = inflater.inflate(R.layout.issue, container, false);
	TextView issue = (TextView) issueView.findViewById(R.id.test_issue);

	issue.setText(issueString[index]);
	
	return issueView;
    }
}
