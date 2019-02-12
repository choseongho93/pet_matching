package mainView.mediaservice;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaServImpl implements MediaServInter {
	private MediaPlayer mediaPlayer;// 음악 재생
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
			public void run() {//////////// 시작되면 실행 제일 처음

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
		mediaPlayer.setOnEndOfMedia(() -> {// 영상이 끝낫을 시점
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
