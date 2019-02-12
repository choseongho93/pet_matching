package mainView.mediaservice;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaServImpl implements MediaServInter {
	private MediaPlayer mediaPlayer;// ���� ���
	private ImageView Play;
	private ImageView Pause;
	private ImageView Stop;
	private boolean endOfMedia;

	private void setController(Parent root) {
		Play = (ImageView) root.lookup("#Play");
		Pause = (ImageView) root.lookup("#Pause");
		Stop = (ImageView) root.lookup("#Stop");
	}

	public void mouseEntered1() {
		Play.setScaleX(1.2);
		Play.setScaleY(1.2);
	}

	public void mouseExited1() {
		Play.setScaleX(1);
		Play.setScaleY(1);
	}

	public void mouseEntered2() {
		Pause.setScaleX(1.2);
		Pause.setScaleY(1.2);
	}

	public void mouseExited2() {
		Pause.setScaleX(1);
		Pause.setScaleY(1);
	}

	public void mouseEntered3() {
		Stop.setScaleX(1.2);
		Stop.setScaleY(1.2);
	}

	public void mouseExited3() {
		Stop.setScaleX(1);
		Stop.setScaleY(1);
	}

	@Override
	public void setMedia(Parent root, String mediaName) {
		Media media = new Media(getClass().getResource(mediaName).toString());
		setController(root);
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {//////////// ���۵Ǹ� ���� ���� ó��

			}
		});
		mediaPlayer.setOnPlaying(() -> {
			Play.setDisable(true);
			Pause.setDisable(false);
			Stop.setDisable(false);
		});
		mediaPlayer.setOnPaused(() -> {
			Play.setDisable(false);
			Pause.setDisable(true);
			Stop.setDisable(false);
		});
		mediaPlayer.setOnEndOfMedia(() -> {// ������ ������ ����
			endOfMedia = true;
		});
		mediaPlayer.setOnStopped(() -> {
			Play.setDisable(false);
			Pause.setDisable(true);
			Stop.setDisable(true);
		});
	}

	@Override
	public void Start() {
		System.out.println("endOfMedia: " + endOfMedia);
		if (endOfMedia) {
			mediaPlayer.stop();
			mediaPlayer.seek(mediaPlayer.getStartTime());
		}
		mediaPlayer.play();
		endOfMedia = false;
	}

	@Override
	public void Pause() {
		mediaPlayer.pause();
	}

	@Override
	public void Stop() {
		mediaPlayer.stop();
	}

	@Override
	public void volumnControl() {
		mediaPlayer.setVolume(50.0);
	}

}
