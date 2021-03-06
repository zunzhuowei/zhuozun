// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: login.proto

package com.qs.game.socket.client.msg;

public final class LoginProto {
  private LoginProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface LoginOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Login)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required string sKey = 1;</code>
     */
    boolean hasSKey();
    /**
     * <code>required string sKey = 1;</code>
     */
    java.lang.String getSKey();
    /**
     * <code>required string sKey = 1;</code>
     */
    com.google.protobuf.ByteString
        getSKeyBytes();

    /**
     * <code>required int32 sCode = 2;</code>
     */
    boolean hasSCode();
    /**
     * <code>required int32 sCode = 2;</code>
     */
    int getSCode();

    /**
     * <code>required int32 mid = 3;</code>
     */
    boolean hasMid();
    /**
     * <code>required int32 mid = 3;</code>
     */
    int getMid();
  }
  /**
   * Protobuf type {@code Login}
   */
  public  static final class Login extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Login)
      LoginOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Login.newBuilder() to construct.
    private Login(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Login() {
      sKey_ = "";
      sCode_ = 0;
      mid_ = 0;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Login(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000001;
              sKey_ = bs;
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              sCode_ = input.readInt32();
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              mid_ = input.readInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.qs.game.socket.client.msg.LoginProto.internal_static_Login_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qs.game.socket.client.msg.LoginProto.internal_static_Login_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qs.game.socket.client.msg.LoginProto.Login.class, com.qs.game.socket.client.msg.LoginProto.Login.Builder.class);
    }

    private int bitField0_;
    public static final int SKEY_FIELD_NUMBER = 1;
    private volatile java.lang.Object sKey_;
    /**
     * <code>required string sKey = 1;</code>
     */
    public boolean hasSKey() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string sKey = 1;</code>
     */
    public java.lang.String getSKey() {
      java.lang.Object ref = sKey_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          sKey_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string sKey = 1;</code>
     */
    public com.google.protobuf.ByteString
        getSKeyBytes() {
      java.lang.Object ref = sKey_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        sKey_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SCODE_FIELD_NUMBER = 2;
    private int sCode_;
    /**
     * <code>required int32 sCode = 2;</code>
     */
    public boolean hasSCode() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required int32 sCode = 2;</code>
     */
    public int getSCode() {
      return sCode_;
    }

    public static final int MID_FIELD_NUMBER = 3;
    private int mid_;
    /**
     * <code>required int32 mid = 3;</code>
     */
    public boolean hasMid() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required int32 mid = 3;</code>
     */
    public int getMid() {
      return mid_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasSKey()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasSCode()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasMid()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, sKey_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeInt32(2, sCode_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt32(3, mid_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, sKey_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, sCode_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, mid_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.qs.game.socket.client.msg.LoginProto.Login)) {
        return super.equals(obj);
      }
      com.qs.game.socket.client.msg.LoginProto.Login other = (com.qs.game.socket.client.msg.LoginProto.Login) obj;

      boolean result = true;
      result = result && (hasSKey() == other.hasSKey());
      if (hasSKey()) {
        result = result && getSKey()
            .equals(other.getSKey());
      }
      result = result && (hasSCode() == other.hasSCode());
      if (hasSCode()) {
        result = result && (getSCode()
            == other.getSCode());
      }
      result = result && (hasMid() == other.hasMid());
      if (hasMid()) {
        result = result && (getMid()
            == other.getMid());
      }
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasSKey()) {
        hash = (37 * hash) + SKEY_FIELD_NUMBER;
        hash = (53 * hash) + getSKey().hashCode();
      }
      if (hasSCode()) {
        hash = (37 * hash) + SCODE_FIELD_NUMBER;
        hash = (53 * hash) + getSCode();
      }
      if (hasMid()) {
        hash = (37 * hash) + MID_FIELD_NUMBER;
        hash = (53 * hash) + getMid();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qs.game.socket.client.msg.LoginProto.Login parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.qs.game.socket.client.msg.LoginProto.Login prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Login}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Login)
        com.qs.game.socket.client.msg.LoginProto.LoginOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.qs.game.socket.client.msg.LoginProto.internal_static_Login_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.qs.game.socket.client.msg.LoginProto.internal_static_Login_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.qs.game.socket.client.msg.LoginProto.Login.class, com.qs.game.socket.client.msg.LoginProto.Login.Builder.class);
      }

      // Construct using com.qs.game.socket.client.msg.LoginProto.Login.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        sKey_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        sCode_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        mid_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.qs.game.socket.client.msg.LoginProto.internal_static_Login_descriptor;
      }

      @java.lang.Override
      public com.qs.game.socket.client.msg.LoginProto.Login getDefaultInstanceForType() {
        return com.qs.game.socket.client.msg.LoginProto.Login.getDefaultInstance();
      }

      @java.lang.Override
      public com.qs.game.socket.client.msg.LoginProto.Login build() {
        com.qs.game.socket.client.msg.LoginProto.Login result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.qs.game.socket.client.msg.LoginProto.Login buildPartial() {
        com.qs.game.socket.client.msg.LoginProto.Login result = new com.qs.game.socket.client.msg.LoginProto.Login(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.sKey_ = sKey_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.sCode_ = sCode_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.mid_ = mid_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return (Builder) super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.qs.game.socket.client.msg.LoginProto.Login) {
          return mergeFrom((com.qs.game.socket.client.msg.LoginProto.Login)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.qs.game.socket.client.msg.LoginProto.Login other) {
        if (other == com.qs.game.socket.client.msg.LoginProto.Login.getDefaultInstance()) return this;
        if (other.hasSKey()) {
          bitField0_ |= 0x00000001;
          sKey_ = other.sKey_;
          onChanged();
        }
        if (other.hasSCode()) {
          setSCode(other.getSCode());
        }
        if (other.hasMid()) {
          setMid(other.getMid());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        if (!hasSKey()) {
          return false;
        }
        if (!hasSCode()) {
          return false;
        }
        if (!hasMid()) {
          return false;
        }
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.qs.game.socket.client.msg.LoginProto.Login parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.qs.game.socket.client.msg.LoginProto.Login) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object sKey_ = "";
      /**
       * <code>required string sKey = 1;</code>
       */
      public boolean hasSKey() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string sKey = 1;</code>
       */
      public java.lang.String getSKey() {
        java.lang.Object ref = sKey_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            sKey_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string sKey = 1;</code>
       */
      public com.google.protobuf.ByteString
          getSKeyBytes() {
        java.lang.Object ref = sKey_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          sKey_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string sKey = 1;</code>
       */
      public Builder setSKey(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        sKey_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string sKey = 1;</code>
       */
      public Builder clearSKey() {
        bitField0_ = (bitField0_ & ~0x00000001);
        sKey_ = getDefaultInstance().getSKey();
        onChanged();
        return this;
      }
      /**
       * <code>required string sKey = 1;</code>
       */
      public Builder setSKeyBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        sKey_ = value;
        onChanged();
        return this;
      }

      private int sCode_ ;
      /**
       * <code>required int32 sCode = 2;</code>
       */
      public boolean hasSCode() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required int32 sCode = 2;</code>
       */
      public int getSCode() {
        return sCode_;
      }
      /**
       * <code>required int32 sCode = 2;</code>
       */
      public Builder setSCode(int value) {
        bitField0_ |= 0x00000002;
        sCode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 sCode = 2;</code>
       */
      public Builder clearSCode() {
        bitField0_ = (bitField0_ & ~0x00000002);
        sCode_ = 0;
        onChanged();
        return this;
      }

      private int mid_ ;
      /**
       * <code>required int32 mid = 3;</code>
       */
      public boolean hasMid() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required int32 mid = 3;</code>
       */
      public int getMid() {
        return mid_;
      }
      /**
       * <code>required int32 mid = 3;</code>
       */
      public Builder setMid(int value) {
        bitField0_ |= 0x00000004;
        mid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 mid = 3;</code>
       */
      public Builder clearMid() {
        bitField0_ = (bitField0_ & ~0x00000004);
        mid_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:Login)
    }

    // @@protoc_insertion_point(class_scope:Login)
    private static final com.qs.game.socket.client.msg.LoginProto.Login DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.qs.game.socket.client.msg.LoginProto.Login();
    }

    public static com.qs.game.socket.client.msg.LoginProto.Login getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<Login>
        PARSER = new com.google.protobuf.AbstractParser<Login>() {
      @java.lang.Override
      public Login parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Login(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Login> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Login> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.qs.game.socket.client.msg.LoginProto.Login getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Login_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Login_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013login.proto\"1\n\005Login\022\014\n\004sKey\030\001 \002(\t\022\r\n\005" +
      "sCode\030\002 \002(\005\022\013\n\003mid\030\003 \002(\005B+\n\035com.qs.game." +
      "socket.client.msgB\nLoginProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Login_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Login_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Login_descriptor,
        new java.lang.String[] { "SKey", "SCode", "Mid", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
