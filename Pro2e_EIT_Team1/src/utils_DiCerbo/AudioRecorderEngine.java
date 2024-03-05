package utils_DiCerbo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorderEngine {

	public interface SampleListener {
		/**
		 * process samples, don't block here
		 * 
		 * @param samples
		 * @param length
		 */
		void onNewSamples(int[] samples, int length);
	}

	private final SampleListener sampleListener;

	public AudioRecorderEngine(SampleListener sampleListener) {
		this.sampleListener = sampleListener;
	}

	private final static int CHUNK_SIZE = 4096;

	private Thread thread;
	private AtomicBoolean started = new AtomicBoolean(false);
	private AtomicBoolean stopping = new AtomicBoolean(false);

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			for (;;) {
				try {
					loop();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("error in audio loop");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (stopping.get()) {
					break;
				}
			}
			stopping.set(false);
			started.set(false);
		}
	};

	public void start() {
		if (started.getAndSet(true)) {
			System.out.println("audio engine already started");
			return;
		}

		thread = new Thread(runnable, "Audio Engine Thread");
		thread.start();
	}

	public void stop() {
		stopping.set(true);
		thread.interrupt();
	}

	private void loop() throws IOException, LineUnavailableException {
		final AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
		TargetDataLine mic;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); // format is an AudioFormat object
		if (!AudioSystem.isLineSupported(info)) {
			throw new IOException("can't open audio line");
		}

		mic = (TargetDataLine) AudioSystem.getLine(info);
		mic.open(format);

		byte[] data = new byte[mic.getBufferSize() / 5];
		System.out.printf("buffersize %d\n", mic.getBufferSize());
		mic.start();

		IntBuffer buffer = ByteBuffer.wrap(data).asIntBuffer();
		int[] target = new int[CHUNK_SIZE / 4];
		int numRead = 0;

		try {
			for (;;) {
				buffer.position(0);
				numRead = mic.read(data, 0, CHUNK_SIZE);
				buffer.get(target);
				sampleListener.onNewSamples(target, numRead / 4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			mic.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
