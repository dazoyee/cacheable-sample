package com.github.ioridazo.cacheable.sample.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;

@Value
public class SampleForm {

    Integer id;

    String name;

    Result result;

    String error;

    // メモリを圧迫させるために無駄に書く
    String uuid1;
    String uuid2;
    String uuid3;
    String uuid4;
    String uuid5;
    String uuid6;
    String uuid7;
    String uuid8;
    String uuid9;
    String uuid10;
    String uuid11;
    String uuid12;
    String uuid13;
    String uuid14;
    String uuid15;
    String uuid16;
    String uuid17;
    String uuid18;
    String uuid19;
    String uuid20;
    String uuid21;
    String uuid22;
    String uuid23;
    String uuid24;
    String uuid25;
    String uuid26;
    String uuid27;
    String uuid28;
    String uuid29;
    String uuid30;
    String uuid31;
    String uuid32;
    String uuid33;
    String uuid34;
    String uuid35;
    String uuid36;
    String uuid37;
    String uuid38;
    String uuid39;
    String uuid40;
    String uuid41;
    String uuid42;
    String uuid43;
    String uuid44;
    String uuid45;
    String uuid46;
    String uuid47;
    String uuid48;
    String uuid49;
    String uuid50;
    String uuid51;

    public static SampleForm ofError(final Exception e){
        return new SampleForm(
                null,
                "cacheable-sample",
                Result.NG,
                e.getMessage(),
                null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,
                null
                );
    }

    enum Result{
        OK,
        NG,
        ;

        @Override
        @JsonValue
        public String toString() {
            return super.toString();
        }
    }
}
