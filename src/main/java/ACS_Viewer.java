/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 import com.usp.ipen.acs.Attenuation_Correction_SPECT.ACS_;
 import ij.*;
 import ij.process.*;
 import ij.plugin.filter.*;

/**
 *
 * @author fmassicano
 */
public class ACS_Viewer implements PlugInFilter{

    @Override
    public int setup(String arg, ImagePlus imp) {
        return DOES_8G + DOES_16 + SUPPORTS_MASKING;
    }
    
    @Override
    public void run(ImageProcessor ip) {
        ACS_.run(ip);
    }

}


