package com.varivoda.igor.quiz_whatcarwillyoudrive.DbFlowBaza;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(version = BazaPodataka.VERSION,name = BazaPodataka.NAME)
public class BazaPodataka {
    @SuppressWarnings("WeakerAccess")
    public static final String NAME = "Korisnik";
    @SuppressWarnings("WeakerAccess")
    public static final int VERSION = 1;
}
