/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sakuracoin.params;

import com.google.sakuracoin.core.NetworkParameters;
import com.google.sakuracoin.core.Utils;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends NetworkParameters {
    public MainNetParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        proofOfWorkLimit = Utils.decodeCompactBits(0x1e0fffffL);
        dumpedPrivateKeyHeader = 178; //This is always addressHeader + 128
        addressHeader = 50;
        //p2shHeader = 5; //We don't have this
        acceptableAddressCodes = new int[] { addressHeader };
        port = 9301;
        packetMagic = 0xfbc0b6db;
        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setTime(0x52b28510L);
        genesisBlock.setNonce(0x000fc9f2L);
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 1051000;
        spendableCoinbaseDepth = 100;
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("4a4ea5016cc89f867500bc2d5bf317e72e0fecb2f4330b2a0921da3cd9bf245b"),
                genesisBlock);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        checkpoints.put( 77500, new Sha256Hash("93cff5764cc7b9af3285d18d98f0162f8ce534d01e7d00efd024c38e5541fd00"));
        checkpoints.put(131644, new Sha256Hash("3fface7e113967b15a68fffdc341a7b5ef356654d2873fd1d475162258841453"));
        checkpoints.put(184818, new Sha256Hash("dfcf2d438c6ecfe2413300a193f8a96628f7b2a23d753e526b80f9f23f9387bc"));

        dnsSeeds = new String[] {
            "skrseed.sighash.info"
        };
    }

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }
}
