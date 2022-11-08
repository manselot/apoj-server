package com.example.demo.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class asd {
    public void writeFromFisToZos(FileInputStream fis, ZipOutputStream zos) throws IOException {
        byte[] buffer = new byte[10000000];
        int length;
        while(true) {
            length = fis.read(buffer);
            if(length < 0) {
                break;
            } else {
                zos.write(buffer, 0, length);
            }
        }
    }
    public void unzip(String archiveName,String dir){
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(dir+archiveName)))
        {
            ZipEntry entry;
            String name;
            long size;
            while((entry=zin.getNextEntry())!=null){

                name = entry.getName(); // получим название файла
                size=entry.getSize();  // получим его размер в байтах
                System.out.printf("File name: %s \t File size: %d \n", name, size);

                // распаковка
                FileOutputStream fout = new FileOutputStream(dir + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

}
