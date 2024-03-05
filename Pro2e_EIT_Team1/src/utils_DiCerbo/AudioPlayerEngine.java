package utils_DiCerbo;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayerEngine {

	final static int FREQ = 44100;
	final SourceDataLine speakers;
	final AudioFormat format = new AudioFormat(FREQ, 16, 1, true, true);

	short[] clear = new short[10 * FREQ];
	short[] buffer = new short[10 * FREQ];
	ByteBuffer byteBuffer = ByteBuffer.allocate(buffer.length * 4);

	public AudioPlayerEngine() throws LineUnavailableException {
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
		speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		speakers.open(format);
		speakers.start();
	}

	public void play(int[] samples) throws IOException {
		if (samples.length > buffer.length) {
			throw new IOException(String.format("max allowed %d samples (each 4byte)", buffer.length));
		}

		System.arraycopy(this.clear, 0, this.buffer, 0, this.buffer.length);
		// System.arraycopy(samples, 0, this.buffer, 0, samples.length);

		for (int i = 0; i < samples.length; i++) {
			this.buffer[i] = (short) (samples[i] >> 16);
		}

		byteBuffer.position(0);
		byteBuffer.asShortBuffer().put(this.buffer);
		// byteBuffer.asIntBuffer().put(samples);

		new Thread(new Runnable() {
			@Override
			public void run() {
				speakers.write(byteBuffer.array(), 0, samples.length * 4);

			}
		}, "Player Thread").start();

	}
}