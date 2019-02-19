package com.whale.excercises4;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IndexFileManager implements IndexManager {

    private RandomAccessFile indexFile;

    public IndexFileManager(String filePath, String mode) throws FileNotFoundException {
        touchOpen(filePath, mode);
    }

    private void touchOpen(String filePath, String mode) throws FileNotFoundException {
        try {
            FileUtils.forceDeleteOnExit(new File(filePath));
            FileUtils.write(new File(filePath), " ", "UTF-8");
        } catch (IOException e) {
            System.out.println("pass");
        }
        this.indexFile = new RandomAccessFile(new File(filePath), mode);
    }

    @Override
    public void record(String term, int pageNum) throws IOException {
        String line = null;
        boolean isExistWord = false;
        Index indexInfo = null;
        while ((line=indexFile.readLine()) != null) {
            if (StringUtils.isEmpty(line.trim())) {
                break;
            }
            if (StringUtils.equals(term, line.split("-")[0].trim())) {
                isExistWord = true;
                String[] index = line.split("-");
                indexInfo = new Index(index[0].trim(), index[1].trim());
                if (indexInfo.getPageNums().size()<100) {
                    indexInfo.getPageNums().add(String.valueOf(pageNum));
                }
                break;
            }
        }
        if (!isExistWord) {
            indexFile.write(String.format("%-250s\n", term + " - " + pageNum).getBytes("UTF-8"));
        } else {
            indexFile.seek(indexFile.getFilePointer()-251);
            indexFile.write(String.format("%-250s\n", indexInfo.toString()).getBytes("UTF-8"));
        }
        indexFile.seek(0);
    }

    @Override
    public void print() {
        String line = null;
        try {
            while ((line = indexFile.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {

        }
    }

    @Override
    public void close() throws IOException {
        if (this.indexFile!=null) {
            this.indexFile.close();
        }
    }
}
