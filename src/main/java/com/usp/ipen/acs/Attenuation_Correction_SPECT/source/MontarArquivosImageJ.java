/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usp.ipen.acs.Attenuation_Correction_SPECT.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *
 * @author fmassicano
 */
public class MontarArquivosImageJ {

    public static void main(String[] args) throws IOException {

        final String slash = System.getProperty("file.separator");
        
        boolean windowsNotUnix = System.getProperty("os.name").contains("Win") ? true: false;
        
        if(windowsNotUnix){
            deleteDir(new File("C:\\Arquivos de programas\\ImageJ\\plugins\\Attenuation_Correction_SPECT"));
            File file = new File("C:\\Arquivos de programas\\ImageJ\\plugins\\Attenuation_Correction_SPECT");
            file.mkdir();
            java.io.File dirOrigem = new java.io.File("F:\\ACPS\\ACPS-v.INAC\\build\\classes\\Attenuation_Correction_SPECT");
            java.io.File dirdestino = new java.io.File("C:\\Arquivos de programas\\ImageJ\\plugins\\Attenuation_Correction_SPECT");
            copyAll(dirOrigem, dirdestino, true);
            // executar o ImageJ
            Runtime.getRuntime().exec("C:\\Arquivos de programas\\ImageJ\\imageJ");
        }else{
            deleteDir(new File(slash+"usr"+slash+"share"+slash+"imagej"+slash+"plugins"+slash+"Attenuation_Correction_SPECT"));
            File file = new File(slash+"usr"+slash+"share"+slash+"imagej"+slash+"plugins"+slash+"Attenuation_Correction_SPECT");
            file.mkdir();
            java.io.File dirOrigem = new java.io.File(slash+"home"+slash+"users"+slash+"fmassicano"+slash+"Dropbox"+slash+"ACS"+slash+"ACPS-v.INAC"+slash+"build"+slash+"classes"+slash+"Attenuation_Correction_SPECT");
            java.io.File dirdestino = new java.io.File(slash+"usr"+slash+"share"+slash+"imagej"+slash+"plugins"+slash+"Attenuation_Correction_SPECT");
            copyAll(dirOrigem, dirdestino, true);
            // executar o ImageJ
            Runtime.getRuntime().exec(slash+"usr"+slash+"bin"+slash+"imagej");
        }
        
    }

    /**
     * Copia arquivos de um local para o outro.
     * @param origem - Arquivo de origem
     * @param destino - Arquivo de destino
     * @param overwrite - Confirmação para sobrescrever os arquivos
     * @throws IOException
     */
    public static void copy(File origem, File destino, boolean overwrite) throws IOException {

        if (destino.exists() && !overwrite) {
            return;
        }

        FileInputStream source = new FileInputStream(origem);
        FileOutputStream destination = new FileOutputStream(destino);

        FileChannel sourceFileChannel = source.getChannel();
        FileChannel destinationFileChannel = destination.getChannel();

        long size = sourceFileChannel.size();
        sourceFileChannel.transferTo(0, size, destinationFileChannel);

    }

    /**
     * Copia todos os arquivos que tenham uma determinada extensão de uma pasta de origem para outra de destino.
     * @param origem - Diretório onde estão os arquivos a serem copiados
     * @param destino - Diretório onde os arquivos serão copiados
     * @param extensao - <i>String</i> Extensão do arquivo que deve ser copiada. Você pode inserir a extensão no formato: ".doc" ou "doc" (por exemplo)
     * @param overwrite - Confirmação para sobrescrever os arquivos
     * @throws IOException, UnsupportedOperationException
     */
    public static void copyAll(File origem, File destino, String extensao, boolean overwrite) throws IOException, UnsupportedOperationException {
       
        String slash = System.getProperty("file.separator");
       
        if (!destino.exists()) {
            destino.mkdir();
        }
        if (!origem.isDirectory()) {
            throw new UnsupportedOperationException("Origem deve ser um diretório");
        }
        if (!destino.isDirectory()) {
            throw new UnsupportedOperationException("Destino deve ser um diretório");
        }
        File[] files = origem.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                copyAll(files[i], new File(destino + slash + files[i].getName()), overwrite);
            } else {
                if (files[i].getName().endsWith(extensao)) {
                    copy(files[i], new File(destino + slash + files[i].getName()), overwrite);
                }
            }
        }
    }

    /**
     * Copia todos os arquivos de dentro de uma pasta para outra.
     * @param origem - Diretório onde estão os arquivos a serem copiados
     * @param destino - Diretório onde os arquivos serão copiados
     * @param overwrite - Confirmação para sobrescrever os arquivos
     * @throws IOException, UnsupportedOperationException
     */
    public static void copyAll(File origem, File destino, boolean overwrite) throws IOException, UnsupportedOperationException {
      String slash = System.getProperty("file.separator");
        if (!destino.exists()) {
            destino.mkdir();
        }
        if (!origem.isDirectory()) {
            throw new UnsupportedOperationException("Origem deve ser um diretório");
        }
        if (!destino.isDirectory()) {
            throw new UnsupportedOperationException("Destino deve ser um diretório");
        }

        File[] files = origem.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                copyAll(files[i], new File(destino + slash + files[i].getName()), overwrite);
            } else {
                System.out.println("Copiando arquivo: " + files[i].getName());
                copy(files[i], new File(destino + slash + files[i].getName()), overwrite);
            }
        }
    }

    /**
     * Deletes all files and subdirectories under dir.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and returns false.
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
}
