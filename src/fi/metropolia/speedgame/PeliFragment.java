package fi.metropolia.speedgame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PeliFragment extends Fragment implements Runnable {

	private ImageView imgSin, imgVih, imgVio, imgPun;
	private Button start;
	private Button stop;
	private TextView tvPisteet, tvHighScore2, tvNopeus;
	private int pisteet = 0, painettavaNappi = 0, laskuri = 0, high_score = 0;
	private ArrayList<Integer> arvotutLuvut = new ArrayList<Integer>();
	private final int aloitusaika = 600;
	private int aika = aloitusaika, uusiaika = 4;
	private volatile boolean kaynnissa = false;
	private SharedPreferences sharedPref;
	private Vibrator vib;
	public static final String MyPREFERENCES = "MyPrefs";
	public static final String Score = "score";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.peli_fragment, container,
				false);

		vib = (Vibrator) getActivity().getSystemService(
				Context.VIBRATOR_SERVICE);
		start = (Button) rootView.findViewById(R.id.buttonStart);
		stop = (Button) rootView.findViewById(R.id.buttonStop);
		imgSin = (ImageView) rootView.findViewById(R.id.imgBlue);
		imgVih = (ImageView) rootView.findViewById(R.id.imgGreen);
		imgVio = (ImageView) rootView.findViewById(R.id.imgPurple);
		imgPun = (ImageView) rootView.findViewById(R.id.imgRed);
		tvPisteet = (TextView) rootView.findViewById(R.id.textViewPisteet);
		tvNopeus = (TextView) rootView.findViewById(R.id.tvNopeus);
		tvHighScore2 = (TextView) rootView.findViewById(R.id.tvHighScore2);

		sharedPref = getActivity().getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		high_score = sharedPref.getInt(Score, 0);
		if (high_score == 0) {
			tvHighScore2.setText(Integer.toString(0));
		} else {
			tvHighScore2.setText(Integer.toString(high_score));
		}

		imgSin.setClickable(true);
		imgVih.setClickable(true);
		imgVio.setClickable(true);
		imgPun.setClickable(true);

		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imgSin.setImageResource(R.drawable.blue_button_default);
				imgVih.setImageResource(R.drawable.green_button_default);
				imgVio.setImageResource(R.drawable.purple_button_default);
				imgPun.setImageResource(R.drawable.red_button_default);
				start.setEnabled(false);
				tvPisteet.setText(Integer.toString(0));
				tvNopeus.setText(Integer.toString(aloitusaika));
				setKaynnissa(true);
				new Thread(PeliFragment.this).start();
			}
		});

		stop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isKaynnissa()) {
					hävittyPeliLopputoimet();
				}
			}
		});

		imgPun.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				ImageView iv = (ImageView) v;

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (painettavaNappi ==1) {
						iv.setImageResource(R.drawable.red_button_pressed_focused);
					} else {
					iv.setImageResource(R.drawable.red_button_pressed);
					}
					if (arvotutLuvut.size() != 0
							&& arvotutLuvut.size() > laskuri) {
						if (arvotutLuvut.get(laskuri) == 1) {
							vib.vibrate(25);
							tvPisteet.setText(Integer.toString(++pisteet));
							laskuri++;
						} else {
							hävittyPeliLopputoimet();
						}
					} else if (arvotutLuvut.size() != 0) {
						hävittyPeliLopputoimet();
					}
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (painettavaNappi ==1) {
						iv.setImageResource(R.drawable.red_button_focused);
					} else {
					iv.setImageResource(R.drawable.red_button_default);
					}
					return true;
				}
				return false;
			}
		});

		imgVio.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				ImageView iv = (ImageView) v;

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (painettavaNappi ==2) {
						iv.setImageResource(R.drawable.purple_button_pressed_focused);
					} else {
					iv.setImageResource(R.drawable.purple_button_pressed);
					}
					if (arvotutLuvut.size() != 0
							&& arvotutLuvut.size() > laskuri) {
						if (arvotutLuvut.get(laskuri) == 2) {
							vib.vibrate(25);
							tvPisteet.setText(Integer.toString(++pisteet));
							laskuri++;
						} else {
							hävittyPeliLopputoimet();
						}
					} else if (arvotutLuvut.size() != 0) {
						hävittyPeliLopputoimet();
					}
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (painettavaNappi ==2) {
						iv.setImageResource(R.drawable.purple_button_focused);
					} else {
					iv.setImageResource(R.drawable.purple_button_default);
					}
					return true;
				}
				return false;
			}
		});

		imgVih.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				ImageView iv = (ImageView) v;

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (painettavaNappi ==3) {
						iv.setImageResource(R.drawable.green_button_pressed_focused);
					} else {
					iv.setImageResource(R.drawable.green_button_pressed);
					}
					if (arvotutLuvut.size() != 0
							&& arvotutLuvut.size() > laskuri) {
						if (arvotutLuvut.get(laskuri) == 3) {
							vib.vibrate(25);
							tvPisteet.setText(Integer.toString(++pisteet));
							laskuri++;
						} else {
							hävittyPeliLopputoimet();
						}
					} else if (arvotutLuvut.size() != 0) {
						hävittyPeliLopputoimet();
					}
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (painettavaNappi ==3) {
						iv.setImageResource(R.drawable.green_button_focused);
					} else {
					iv.setImageResource(R.drawable.green_button_default);
					}
					return true;
				}
				return false;
			}
		});

		imgSin.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				ImageView iv = (ImageView) v;

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (painettavaNappi ==4) {
						iv.setImageResource(R.drawable.blue_button_pressed_focused);
					} else {
					iv.setImageResource(R.drawable.blue_button_pressed);
					}
					if (arvotutLuvut.size() != 0
							&& arvotutLuvut.size() > laskuri) {
						if (arvotutLuvut.get(laskuri) == 4) {
							vib.vibrate(25);
							tvPisteet.setText(Integer.toString(++pisteet));
							laskuri++;
						} else {
							hävittyPeliLopputoimet();
						}
					} else if (arvotutLuvut.size() != 0) {
						hävittyPeliLopputoimet();
					}
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (painettavaNappi ==4) {
						iv.setImageResource(R.drawable.blue_button_focused);
					} else {
					iv.setImageResource(R.drawable.blue_button_default);
					}
					return true;
				}
				return false;
			}
		});

		return rootView;
	}

	@Override
	public void run() {
		while (aika > 0 && isKaynnissa()) {
			try {
				Thread.sleep(aika);
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (isKaynnissa()) {
							tvNopeus.setText(Integer.toString(aika));
							arvoNappi();
						}
					}
				});

			} catch (InterruptedException e) {
				return;
			} catch (Exception e) {
				return;
			}
			if (aika <= 200) {
				uusiaika = 0;
			} else if (aika < 350) {
				uusiaika = 1;
			} else if (aika < 450) {
				uusiaika = 2;
			} else if (aika < 550) {
				uusiaika = 3;
			}
			aika = aika - uusiaika;
		}
		aika = aloitusaika;
		uusiaika = 4;
		arvotutLuvut.clear();

	}

	public boolean isKaynnissa() {
		return kaynnissa;
	}

	public void setKaynnissa(boolean kaynnissa) {
		this.kaynnissa = kaynnissa;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	public void arvoNappi() {
		if (painettavaNappi != 0) {
			switch (painettavaNappi) {
			case 1:
				imgPun.setImageResource(R.drawable.red_button_default);
				break;
			case 2:
				imgVio.setImageResource(R.drawable.purple_button_default);
				break;
			case 3:
				imgVih.setImageResource(R.drawable.green_button_default);
				break;
			case 4:
				imgSin.setImageResource(R.drawable.blue_button_default);
				break;
			}
		}
		Random random = new Random();
		int uusirandom = (int) (4 * random.nextDouble() + 1);
		while (uusirandom == painettavaNappi) {
			uusirandom = (int) (4 * random.nextDouble() + 1);
		}
		painettavaNappi = uusirandom;
		// tvrandom.setText(Integer.toString(painettavaNappi));

		if (painettavaNappi == 1) {
			arvotutLuvut.add(1);
			imgPun.setImageResource(R.drawable.red_button_focused);
		} else if (painettavaNappi == 2) {
			arvotutLuvut.add(2);
			imgVio.setImageResource(R.drawable.purple_button_focused);
		} else if (painettavaNappi == 3) {
			arvotutLuvut.add(3);
			imgVih.setImageResource(R.drawable.green_button_focused);
		} else {
			arvotutLuvut.add(4);
			imgSin.setImageResource(R.drawable.blue_button_focused);
		}

	}

	public void hävittyPeliLopputoimet() {
		start.setEnabled(true);
		vib.vibrate(750);
		Toast toast = Toast.makeText(getActivity().getApplicationContext(),
				getString(R.string.game_over1) + " " + pisteet + " "
						+ getString(R.string.game_over2), Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

		setKaynnissa(false);
		arvotutLuvut.clear();

		sharedPref = getActivity().getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		high_score = sharedPref.getInt(Score, 0);
		if (pisteet > high_score) {
			sharedPref = getActivity().getSharedPreferences(MyPREFERENCES,
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt(Score, pisteet);
			editor.commit();
			tvHighScore2.setText(Integer.toString(pisteet));
		}
		aika = aloitusaika;
		uusiaika = 4;
		laskuri = 0;
		pisteet = 0;
		painettavaNappi = 0;

		imgSin.setImageResource(R.drawable.blue_button_default);
		imgVih.setImageResource(R.drawable.green_button_default);
		imgVio.setImageResource(R.drawable.purple_button_default);
		imgPun.setImageResource(R.drawable.red_button_default);

	}

}
