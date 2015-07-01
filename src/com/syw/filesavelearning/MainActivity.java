package com.syw.filesavelearning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
/*
 * ���ݳ־ô洢һ����Ϊ�����ļ��洢:�ļ��洢��SharedPreference�洢�����ݿ�洢
 * ���������ļ��洢������ͨ��openFileOutput("data",Context.MODE_PRIVATE)
 * ������ļ���������·����Ĭ��·��Ϊdata/data/<package name>/files/�£�����֮�⣬����׷��ģʽMODE_APPEND
 * ����ͨ��openFileOutput()��openFileInput()����FileOuputStream��FileInputStrem����Ȼ��������Java���������ļ����ж�д������
 */
public class MainActivity extends Activity {
	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.editTextId);
		String loader = load();
		/*
		 * TextUtils.isEmpty()һ���Խ������ֿ�ֵ���жϣ�������null�Ϳ��ַ���ʱ������������᷵��true��
		 */
		if (!TextUtils.isEmpty(loader)) {
			editText.setText(loader);
			editText.setSelection(loader.length());
			Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		saveData();
	}

	private void saveData() {
		FileOutputStream out = null;
		BufferedWriter writer = null;
		try {
			out = openFileOutput("data", Context.MODE_PRIVATE);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			String data = editText.getText().toString();
			Log.d("TAG", data);
			writer.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();// ���رն�д�޷����
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String load() {
		StringBuilder builder = new StringBuilder();
		String line = "";
		FileInputStream in = null;
		BufferedReader reader = null;
		try {
			in = openFileInput("data");
			reader = new BufferedReader(new InputStreamReader(in));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return builder.toString();
	}

}
