package io.dacopancm.socketdcm.helper;
/*
 *	SimpleAudioRecorder.java
 *
 *	This file is part of jsresources.org
 */

/*
 * Copyright (c) 1999 - 2003 by Matthias Pfisterer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 |<---            this code is formatted to fit into 80 columns             --->|
 */
import io.dacopancm.socketdcm.view.AudioRecDialogController;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * <titleabbrev>SimpleAudioRecorder</titleabbrev>
 * <title>Recording to an audio file (simple version)</title>
 *
 * <formalpara><title>Purpose</title>
 * <para>Records audio data and stores it in a file. The data is recorded in CD
 * quality (44.1 kHz, 16 bit linear, stereo) and stored in a
 * <filename>.wav</filename> file.</para></formalpara>
 *
 * <formalpara><title>Usage</title>
 * <para>
 * <cmdsynopsis>
 * <command>java SimpleAudioRecorder</command>
 * <arg choice="plain"><option>-h</option></arg>
 * </cmdsynopsis>
 * <cmdsynopsis>
 * <command>java SimpleAudioRecorder</command>
 * <arg choice="plain"><replaceable>audiofile</replaceable></arg>
 * </cmdsynopsis>
 * </para></formalpara>
 *
 * <formalpara><title>Parameters</title>
 * <variablelist>
 * <varlistentry>
 * <term><option>-h</option></term>
 * <listitem><para>print usage information, then exit</para></listitem>
 * </varlistentry>
 * <varlistentry>
 * <term><option><replaceable>audiofile</replaceable></option></term>
 * <listitem><para>the file name of the audio file that should be produced from
 * the recorded data</para></listitem>
 * </varlistentry>
 * </variablelist>
 * </formalpara>
 *
 * <formalpara><title>Bugs, limitations</title>
 * <para>
 * You cannot select audio formats and the audio file type on the command line.
 * See AudioRecorder for a version that has more advanced options. Due to a bug
 * in the Sun jdk1.3/1.4, this program does not work with it.
 * </para></formalpara>
 *
 * <formalpara><title>Source code</title>
 * <para>
 * <ulink url="SimpleAudioRecorder.java.html">SimpleAudioRecorder.java</ulink>
 * </para>
 * </formalpara>
 *
 */
public class SimpleAudioRecorder
        extends Thread {

    private TargetDataLine m_line;
    private AudioFileFormat.Type m_targetType;
    private AudioInputStream m_audioInputStream;
    private File m_outputFile;

    public boolean isRuning;
    private final Logger logger;
    AudioRecDialogController controller;
    private final String filename;

    public SimpleAudioRecorder(TargetDataLine line,
            AudioFileFormat.Type targetType, String filename,
            File file) {
        m_line = line;
        m_audioInputStream = new AudioInputStream(line);
        m_targetType = targetType;
        m_outputFile = file;
        logger = Logger.getLogger("practical");
        this.filename = filename;
    }

    public SimpleAudioRecorder(AudioRecDialogController controller, String filename) {
        this.filename = filename;
        logger = Logger.getLogger("practical");
        this.controller = controller;
    }

    /**
     * Starts the recording. To accomplish this, (i) the line is started and
     * (ii) the thread is started.
     */
    @Override
    public void start() {
        isRuning = true;
        /* Starting the TargetDataLine. It tells the line that
         we now want to read data from it. If this method
         isn't called, we won't
         be able to read data from the line at all.
         */
        m_line.start();

        /* Starting the thread. This call results in the
         method 'run()' (see below) being called. There, the
         data is actually read from the line.
         */
        super.start();
    }

    /**
     * Stops the recording.
     *
     * Note that stopping the thread explicitely is not necessary. Once no more
     * data can be read from the TargetDataLine, no more data be read from our
     * AudioInputStream. And if there is no more data from the AudioInputStream,
     * the method 'AudioSystem.write()' (called in 'run()' returns. Returning
     * from 'AudioSystem.write()' is followed by returning from 'run()', and
     * thus, the thread is terminated automatically.
     *
     * It's not a good idea to call this method just 'stop()' because stop() is
     * a (deprecated) method of the class 'Thread'. And we don't want to
     * override this method.
     */
    public void stopRecording() {
        if (isRuning) {
            m_line.stop();
            m_line.close();
        }
    }

    /**
     * Main working method. You may be surprised that here, just
     * 'AudioSystem.write()' is called. But internally, it works like this:
     * AudioSystem.write() contains a loop that is trying to read from the
     * passed AudioInputStream. Since we have a special AudioInputStream that
     * gets its data from a TargetDataLine, reading from the AudioInputStream
     * leads to reading from the TargetDataLine. The data read this way is then
     * written to the passed File. Before writing of audio data starts, a header
     * is written according to the desired audio file type. Reading continues
     * untill no more data can be read from the AudioInputStream. In our case,
     * this happens if no more data can be read from the TargetDataLine. This,
     * in turn, happens if the TargetDataLine is stopped or closed (which
     * implies stopping). (Also see the comment above.) Then, the file is closed
     * and 'AudioSystem.write()' returns.
     */
    @Override
    public void run() {
        try {
            AudioSystem.write(
                    m_audioInputStream,
                    m_targetType,
                    m_outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            /* We have made shure that there is only one command line
             argument. This is taken as the filename of the soundfile
             to store to.
             */
            File outputFile = new File(filename);
            if (outputFile.exists()) {
                outputFile.delete();
            }
            outputFile.createNewFile();

            /* For simplicity, the audio data format used for recording
             is hardcoded here. We use PCM 44.1 kHz, 16 bit signed,
             stereo.
             */
            AudioFormat audioFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100.0F, 16, 2, 4, 44100.0F, false);

            /* Now, we are trying to get a TargetDataLine. The
             TargetDataLine is used later to read audio data from it.
             If requesting the line was successful, we are opening
             it (important!).
             */
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            //TargetDataLine m_line = null;
            try {
                m_line = (TargetDataLine) AudioSystem.getLine(info);
                m_line.open(audioFormat);
            } catch (LineUnavailableException e) {
                out("unable to get a recording line");
                e.printStackTrace();
                System.exit(1);
            }

            /* Again for simplicity, we've hardcoded the audio file
             type, too.
             */
            AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;

            /* Now, we are creating an SimpleAudioRecorder object. It
             contains the logic of starting and stopping the
             recording, reading audio data from the TargetDataLine
             and writing the data to a file.
             */
            m_targetType = targetType;
            m_audioInputStream = new AudioInputStream(m_line);
            m_outputFile = outputFile;

            start();
        } catch (Exception ex) {
            isRuning = false;
            out("error al grabar audio");
            controller.handleCancel();

        }

    }

    private void out(String strMessage) {
        HelperUtil.showErrorB(strMessage);
        logger.log(Level.INFO, strMessage);

    }
}

/**
 * * SimpleAudioRecorder.java **
 */
