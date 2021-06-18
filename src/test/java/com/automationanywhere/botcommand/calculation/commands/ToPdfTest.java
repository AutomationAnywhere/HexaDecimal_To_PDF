package com.automationanywhere.botcommand.calculation.commands;

import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.samples.commands.basic.ToPdf;
import org.apache.commons.codec.DecoderException;

public class ToPdfTest {
    String hex = "sdfsdf";

    ToPdf toPdf = new ToPdf();
    StringValue result = (StringValue) toPdf.action(hex,"");

    public ToPdfTest() throws DecoderException {
    }
}
