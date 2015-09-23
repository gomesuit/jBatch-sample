package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;

import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;

public class MyReader implements ItemReader {
    private MyCheckpoint checkpoint;
    private BufferedReader breader;
    
    JobContext jobCtx;

    public MyReader() {}

    @Override
    public void open(Serializable ckpt) throws Exception {
        if (ckpt == null)
            checkpoint = new MyCheckpoint();
        else
            checkpoint = (MyCheckpoint) ckpt;
        String fileName = jobCtx.getProperties()
                                .getProperty("input_file");
        breader = new BufferedReader(new FileReader(fileName));
        for (long i=0; i<checkpoint.getLineNum(); i++)
            breader.readLine();
    }

    @Override
    public void close() throws Exception {
        breader.close();
    }

    @Override
    public Object readItem() throws Exception {
        String line = breader.readLine();
        return line;
    }

	@Override
	public Serializable checkpointInfo() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
